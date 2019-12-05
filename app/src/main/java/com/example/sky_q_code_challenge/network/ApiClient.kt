package com.example.sky_q_code_challenge.network

import com.example.sky_q_code_challenge.data.Movies
import io.reactivex.Observable
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object  ApiClient {

   // var apiService : ApiService? = null

  fun callApi() : Observable<Movies> {
      return  Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(MoshiConverterFactory.create())
          .baseUrl(Const.BASE_URL)
          .build()
          .create<ApiService>(ApiService::class.java)
          .getMovies()
  }


   /* fun setupRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpCacheDirectory = File(getAppContext()?.cacheDir, "offlineData")

        //10 MB cache data
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(provideCacheInterceptor())
            .addInterceptor(provideOfflineCacheInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .baseUrl(Const.BASE_URL)
            .build()
             apiService = retrofit.create<ApiService>(ApiService::class.java)
            *//*.create<ApiService>(ApiService::class.java)*//*
    }

    private fun provideCacheInterceptor(): Interceptor? {
        return Interceptor { chain ->
            var request = chain.request()
            val originalResponse = chain.proceed(request)
            val cacheControl = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                    "no-cache"
                ) ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
            ) {
                val cc = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                *//*return originalResponse.newBuilder()
                                .header("Cache-Control", "public, max-stale=" + 60 * 60 * 24)
                                .build();*//*
                request = request.newBuilder()
                    .cacheControl(cc)
                    .build()
                chain.proceed(request)
            } else {
                originalResponse
            }
        }
    }

    private fun provideOfflineCacheInterceptor(): Interceptor? {
        return Interceptor { chain ->
            try {
                return@Interceptor chain.proceed(chain.request())
            } catch (e: Exception) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                val offlineRequest = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .build()
                return@Interceptor chain.proceed(offlineRequest)
            }
        }
    }
*/

    // stackoverflow
    /*fun setup() {
        val cacheFile = File(getAppContext()?.cacheDir, "offlineData")
        val cache = Cache(cacheFile, 10 * 1024 * 1024)
        val client = OkHttpClient().newBuilder()
          //  .cache(cache)
            .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
            .build()

       var  mRetrofit = Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        apiService = mRetrofit.create<ApiService>(ApiService::class.java)
    }*/

    private val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                "no-cache"
            ) ||
            cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
        ) {
            originalResponse.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 5000)
                .build()
        } else {
            originalResponse
        }
    }

    private val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE =
        Interceptor { chain ->
            var request = chain.request()
                request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .build()
            chain.proceed(request)
        }


}