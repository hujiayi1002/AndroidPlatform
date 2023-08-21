# AndroidPlatform
    AndroidPlatform
    使用方式

    android内添加

    kotlinOptions {
        jvmTarget = '1.8'
    }
    viewBinding {
        enabled = true
    }
    dataBinding {
        enabled = true
    }

    allprojects {
        repositories {
            google()
            jcenter()
            mavenCentral()
            maven { url 'https://jitpack.io' }
            maven { url "https://maven.aliyun.com/repository/public" }
            maven { url 'https://maven.aliyun.com/nexus/content/groups/public' }
            maven { url 'https://maven.aliyun.com/repository/public' }
            maven { url 'https://maven.aliyun.com/repository/google' }
            maven { url 'https://maven.aliyun.com/repository/jcenter' }
            maven { url 'https://maven.aliyun.com/repository/central' }
        }
    }


    dependencies {
	        implementation 'com.github.hujiayi1002:AndroidPlatform:Tag'
    }
    class viewModel: BaseViewModel(){
        val token by lazy { MutableLiveData<LoginBean>() }
        val parameter = HashMap<String, Any>()
        launch({
           liveData.postValue(ApiRetrofit.instance.getToken(parameter))
        }, {
          liveData.postValue(null)
        })
    }

    class activity:: BaseVMActivity<ActivityMainBinding, viewModel>(){
        viewModel.getToken.observe(this, Observer {
        Log.e("TAG", "onCreate: ,${it == null} ")
        it?.let {
             dataBinding.user = it
            }
        })
    }
    class ApiRetrofit:BaseRetrofit() {
        companion object {
            val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
             ApiRetrofit()
        }
        private fun getApiService(): ApiService {
            val token = SharePreferenceUtil.getString("token")
            MyLog.e("hu--token", token)
            val params: HashMap<String, Any> = HashMap()
            if (token.isNullOrEmpty()) {
                params["csrf-csrf"] = "csrf-csrf"
                params["Content-Type"] = "application/x-www-form-urlencoded"
            } else {
                 params["csrf-csrf"] = "csrf-csrf"
                  params["Authorization"] = "Bearer  $token"
            }
            instance.addHeader(params)
            return instance.createService(ApiService::class.java)
            }
        }   
        fun login(
            user: String,
            password: String,
            ): Observable<UserBean> {
                val params: MutableMap<String, Any> = HashMap()
                params["username"] = user
                params["grant_type"] = "password"
                params["scope"] = "read"
                params["password"] = password
                params["client_id"] = "my-client"
                params["openid"] = "123456789"
                return switchSchedulers(getApiService().login(params))
        }
    }

    interface ApiService {
        @FormUrlEncoded
        @POST("oauth/token")
        suspend fun login(@FieldMap map: Map<String, @JvmSuppressWildcards Any>): TokenBean
    }