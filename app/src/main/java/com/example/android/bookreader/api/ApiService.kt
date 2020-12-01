package com.example.android.bookreader.api

import java.io.InputStream
import java.net.URL

// this class is a very simplified version for a possible api service. For this demo the only
// needed endpoint is very simple and generic, Retrofit would definitely be an overkill.
// I should have also overcome the limitation that is not possible to create a basic Retrofit
// instance without a base URL
open class ApiService {

    open fun getBook(url: String) : InputStream {
        return URL(url).openStream()
    }

}
