# VettelGank
Vettel's Gank 是一款完全使用 **Kotlin** 作为编程语言实现的 **[干货集中营](http://gank.io/)** 客户端。本项目展示了一些Kotlin的优秀语言特性，并且分别使用 **MVP、MVVM** 两种方式来实现；使用了Jetpack中的Architecture Components以及AndroidX，你可以Checkout到不同分支来对比它们之间的实现区别。  
  
### 分支含义 
1. **kotlin-mvp-base:** 使用 Kotlin + MVP 实现的基础版本，此分支主要展示了Kotlin基础的使用，对于入门Kotlin比较友好  
2. **kotlin-mvvm-jetpack:** 使用 Kotlin + MVVM 实现，展示了Android Jetpack中架构组件的使用（包括：DataBinding、LiveData、ViewModel、 Room）  
3. **kotlin-mvvm-jetpack-paging:** 在kotlin-mvvm-jetpack基础上，增加Paging作为App分页库，可以更加优雅地加载无限列表  
  
### 架构规范
* MVP: 参考 [android-architecture](https://github.com/googlesamples/android-architecture) 中的 [todo-mvp-kotlin](https://github.com/googlesamples/android-architecture/tree/todo-mvp-kotlin)  
* MVVM: 参考 [android-architecture](https://github.com/googlesamples/android-architecture) 中的 [todo-mvvm-live-kotlin](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-live-kotlin) 
  
### 效果

### 开源库
* [Data Binding](https://developer.android.google.cn/topic/libraries/data-binding/)  
* [Room](https://developer.android.google.cn/topic/libraries/architecture/room)  
* [LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata)  
* [ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel)  
* [Paging](https://developer.android.google.cn/topic/libraries/architecture/paging/)  
* [AndroidX](https://developer.android.google.cn/jetpack/androidx)  
* [Anko](https://github.com/Kotlin/anko)  
* [Retrofit](https://github.com/square/retrofit)  
* [Glide](https://github.com/bumptech/glide)  
* [PhotoView](https://github.com/chrisbanes/PhotoView)  
* [Gson](https://github.com/google/gson)

### 图标/设计
* [Material Design Icons](https://github.com/google/material-design-icons)  
* [Squareplex](https://www.iconfinder.com/iconsets/squareplex)

### 参考
* [todo-mvp-kotlin](https://github.com/googlesamples/android-architecture/tree/todo-mvp-kotlin)  
* [todo-mvvm-live-kotlin](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-live-kotlin)  
* [PagingWithNetworkSample](https://github.com/googlesamples/android-architecture-components/tree/master/PagingWithNetworkSample)
