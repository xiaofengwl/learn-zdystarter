package com.zdy.mystarter.spring.factory.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.NamedBeanHolder;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO 跟踪学习·DefaultListableBeanFactory
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/29 10:08
 * @desc
 */
public class ZDYDefaultListableBeanFactory extends DefaultListableBeanFactory{

    /**
     * javax注入加载类,允许为Null
     */
    @Nullable
    private static Class<?> javaxInjectProviderClass;

    /**
     *
     */
    static{
        try{
            //todo 自定义子加载器进行个性化加载：通过反射机制实现动态加载,要指定一个类加载器
            javaxInjectProviderClass=ClassUtils.forName("javax.inject.Provider",
                    ZDYDefaultListableBeanFactory.class.getClassLoader());
        }catch (Exception e){
            // JSR-330 API not available - Provider interface simply not supported then.
            javaxInjectProviderClass = null;
        }
    }

    /**
     * 工厂实例容器
     */
    private static final Map<String,Reference<DefaultListableBeanFactory>> serializableFactories=
            new ConcurrentHashMap<>();

    /**
     * 这个工厂的可选id，用于序列化。
     */
    @Nullable
    private String serializationId;

    /**
     * 是否允许用相同的名称重新注册不同的定义，就是重复定义会覆盖前面定义的Bean
     */
    private boolean allowBeanDefinitionOverriding = true;

    /**
     * 即使对于延迟初始化bean，是否允许立即加载类
     * todo  疑惑，为啥这么做呢？
     */
    private boolean allowEagerClassLoading = true;

    /**
     * 可选的OrderComparator，用于依赖列表和数组.
     */
    @Nullable
    private Comparator<Object> dependencyComparator;

    /**
     * 解析器，用于检查bean定义是否为自动装配候选。
     */
    private AutowireCandidateResolver autowireCandidateResolver = new SimpleAutowireCandidateResolver();

    /**
     * 从依赖类型映射到相应的自动生成值e.
     */
    private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<>(16);

    /**
     * bean定义对象的映射，由bean名称键控.
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 单例和非单例bean名称的映射，由依赖项类型键控.
     */
    private final Map<Class<?>, String[]> allBeanNamesByType = new ConcurrentHashMap<>(64);

    /**
     * 单例bean名称的映射，由依赖项类型键控.
     */
    private final Map<Class<?>, String[]> singletonBeanNamesByType = new ConcurrentHashMap<>(64);

    /**
     * bean定义名称列表，按注册顺序排列。
     */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    /**
     * 手动注册的单例名称，按注册顺序排列.
     */
    private volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);

    /**
     * 缓存的bean定义名数组，以防冻结配置。
     */
    @Nullable
    private volatile String[] frozenBeanDefinitionNames;

    /**
     * 是否可以缓存所有bean的bean定义元数据.
     */
    private volatile boolean configurationFrozen = false;

    /**
     * 构造器
     */
    public ZDYDefaultListableBeanFactory(){
        super();
    }

    /**
     * 使用给定的父类创建一个新的DefaultListableBeanFactory。
     * @param parentBeanFactory
     */
    public ZDYDefaultListableBeanFactory(@Nullable BeanFactory parentBeanFactory){
        super(parentBeanFactory);
    }

    //todo  getter、setter方法
    /**装载工程实例*/
    public void setSerializationId(@Nullable String serializationId) {
        if (serializationId != null) {
            serializableFactories.put(serializationId, new WeakReference<>(this));
        }
        else if (this.serializationId != null) {
            serializableFactories.remove(this.serializationId);
        }
        this.serializationId = serializationId;
    }
    @Nullable
    public String getSerializationId() {
        return this.serializationId;
    }
    public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
        this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
    }
    public boolean isAllowBeanDefinitionOverriding() {
        return this.allowBeanDefinitionOverriding;
    }
    public void setAllowEagerClassLoading(boolean allowEagerClassLoading) {
        this.allowEagerClassLoading = allowEagerClassLoading;
    }
    public boolean isAllowEagerClassLoading() {
        return this.allowEagerClassLoading;
    }
    public void setDependencyComparator(@Nullable Comparator<Object> dependencyComparator) {
        this.dependencyComparator = dependencyComparator;
    }
    @Nullable
    public Comparator<Object> getDependencyComparator() {
        return this.dependencyComparator;
    }
    /**设置解析器**/
    public void setAutowireCandidateResolver(final AutowireCandidateResolver autowireCandidateResolver) {
        Assert.notNull(autowireCandidateResolver, "AutowireCandidateResolver must not be null");
        if (autowireCandidateResolver instanceof BeanFactoryAware) {
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                    ((BeanFactoryAware) autowireCandidateResolver).setBeanFactory(ZDYDefaultListableBeanFactory.this);
                    return null;
                }, getAccessControlContext());
            } else {
                ((BeanFactoryAware) autowireCandidateResolver).setBeanFactory(this);
            }
        }
        this.autowireCandidateResolver = autowireCandidateResolver;

    }
    public AutowireCandidateResolver getAutowireCandidateResolver() {
        return this.autowireCandidateResolver;
    }


    /**
     * 直接拷贝参数项
     * @param otherFactory
     */
    @Override
    public void copyConfigurationFrom(ConfigurableBeanFactory otherFactory) {
        super.copyConfigurationFrom(otherFactory);
        if (otherFactory instanceof DefaultListableBeanFactory) {
            ZDYDefaultListableBeanFactory otherListableFactory = (ZDYDefaultListableBeanFactory) otherFactory;
            this.allowBeanDefinitionOverriding = otherListableFactory.allowBeanDefinitionOverriding;
            this.allowEagerClassLoading = otherListableFactory.allowEagerClassLoading;
            this.dependencyComparator = otherListableFactory.dependencyComparator;
            // A clone of the AutowireCandidateResolver since it is potentially BeanFactoryAware...
            setAutowireCandidateResolver(
                    BeanUtils.instantiateClass(otherListableFactory.getAutowireCandidateResolver().getClass()));
            // Make resolvable dependencies (e.g. ResourceLoader) available here as well...
            this.resolvableDependencies.putAll(otherListableFactory.resolvableDependencies);
        }
    }

    //---------------------------------------------------------------------
    // todo Implementation of remaining BeanFactory methods
    //---------------------------------------------------------------------

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBean(requiredType, (Object[]) null);
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(Class<T> requiredType, @Nullable Object... args) throws BeansException {
        Assert.notNull(requiredType, "Required type must not be null");
        Object resolved = resolveBean(ResolvableType.forRawClass(requiredType), args, false);
        if (resolved == null) {
            throw new NoSuchBeanDefinitionException(requiredType);
        }
        return (T) resolved;
    }
    @Nullable
    private <T> T resolveBean(ResolvableType requiredType, @Nullable Object[] args, boolean nonUniqueAsNull) {
        NamedBeanHolder<T> namedBean = resolveNamedBean(requiredType, args, nonUniqueAsNull);
        if (namedBean != null) {
            return namedBean.getBeanInstance();
        }
        BeanFactory parent = getParentBeanFactory();
        if (parent instanceof DefaultListableBeanFactory) {
            return ((ZDYDefaultListableBeanFactory) parent).resolveBean(requiredType, args, nonUniqueAsNull);
        }
        else if (parent != null) {
            ObjectProvider<T> parentProvider = parent.getBeanProvider(requiredType);
            if (args != null) {
                return parentProvider.getObject(args);
            }
            else {
                return (nonUniqueAsNull ? parentProvider.getIfUnique() : parentProvider.getIfAvailable());
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <T> NamedBeanHolder<T> resolveNamedBean(
            ResolvableType requiredType, @Nullable Object[] args, boolean nonUniqueAsNull) throws BeansException {

        Assert.notNull(requiredType, "Required type must not be null");
        String[] candidateNames = getBeanNamesForType(requiredType);

        if (candidateNames.length > 1) {
            List<String> autowireCandidates = new ArrayList<>(candidateNames.length);
            for (String beanName : candidateNames) {
                if (!containsBeanDefinition(beanName) || getBeanDefinition(beanName).isAutowireCandidate()) {
                    autowireCandidates.add(beanName);
                }
            }
            if (!autowireCandidates.isEmpty()) {
                candidateNames = StringUtils.toStringArray(autowireCandidates);
            }
        }

        if (candidateNames.length == 1) {
            String beanName = candidateNames[0];
            return new NamedBeanHolder<>(beanName, (T) getBean(beanName, requiredType.toClass(), args));
        }
        else if (candidateNames.length > 1) {
            Map<String, Object> candidates = new LinkedHashMap<>(candidateNames.length);
            for (String beanName : candidateNames) {
                if (containsSingleton(beanName) && args == null) {
                    Object beanInstance = getBean(beanName);
                    candidates.put(beanName, (beanInstance instanceof com.zdy.mystarter.spring.factory.NullBean ? null : beanInstance));
                }
                else {
                    candidates.put(beanName, getType(beanName));
                }
            }
            String candidateName = determinePrimaryCandidate(candidates, requiredType.toClass());
            if (candidateName == null) {
                candidateName = determineHighestPriorityCandidate(candidates, requiredType.toClass());
            }
            if (candidateName != null) {
                Object beanInstance = candidates.get(candidateName);
                if (beanInstance == null || beanInstance instanceof Class) {
                    beanInstance = getBean(candidateName, requiredType.toClass(), args);
                }
                return new NamedBeanHolder<>(candidateName, (T) beanInstance);
            }
            if (!nonUniqueAsNull) {
                throw new NoUniqueBeanDefinitionException(requiredType, candidates.keySet());
            }
        }

        return null;
    }

}
