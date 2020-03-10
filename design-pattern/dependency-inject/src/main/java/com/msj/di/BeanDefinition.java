package com.msj.di;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-10
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc 配置元信息
 */
public class BeanDefinition {

    private String id;

    private String className;

    private List<ConstructorArg> constructorArgs = new ArrayList<>();

    private Scope scope = Scope.SINGLETON;

    private boolean lazy = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }

    public void addConstructorArg(ConstructorArg constructorArg) {
        this.constructorArgs.add(constructorArg);
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public boolean isSingleton(){
        if(this.scope == Scope.SINGLETON){
            return true;
        }
        return false;
    }

    public static enum Scope{
        SINGLETON,
        PROTOTYPE
    }

    /**
     * 使用建造者模式，可以在build中检测逻辑
     */
    public static class ConstructorArg{

        private boolean ref;

        private Class type;

        private Object arg;

        public boolean isRef() {
            return ref;
        }

        public Class getType() {
            return type;
        }

        public Object getArg() {
            return arg;
        }

        private ConstructorArg(Builder builder){
            this.ref = builder.ref;
            this.type = builder.type;
            this.arg = builder.arg;
        }

        public static class Builder{

            private boolean ref = false;

            private Class type;

            private Object arg;

            public ConstructorArg build(){
                if(this.isRef()){
                    if(null != this.type){
                        throw new IllegalArgumentException("当参数为引用类型时，无需设置 type 参数");
                    }

                    //引用时，arg取的是引用ID
                    if(!(arg instanceof String)){
                        throw new IllegalArgumentException("请设置引用ID");
                    }
                }else{
                    //非引用时 arg取的是value值
                    if (null == this.type || null == this.arg) {
                        throw new IllegalArgumentException("当参数为非引用类型时，type 和 arg 参数必填");
                    }
                }

                return new ConstructorArg(this);
            }

            public boolean isRef() {
                return ref;
            }

            public Builder setRef(boolean ref) {
                this.ref = ref;
                return this;
            }

            public Class getType() {
                return type;
            }

            public Builder setType(Class type) {
                this.type = type;
                return this;
            }

            public Object getArg() {
                return arg;
            }

            public Builder setArg(Object arg) {
                this.arg = arg;
                return this;
            }
        }
    }
}
