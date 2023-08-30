package com.yerayyas.pagingmarvelapi.utils

object Constants {
   const val PAGE_SIZE = 20
   private const val MARVEL_PRIVATE_API_KEY = "214c207509b1ed4c5d6456ad38a6ff91f382ead4"
   private const val MARVEL_PUBLIC_API_KEY = "3de6bbd5de0a40038da2c8fe677fb23b"
   const val BASE_URL = "https://gateway.marvel.com/"
   const val API_KEY = "3de6bbd5de0a40038da2c8fe677fb23b"
   val TS = System.currentTimeMillis()
   val HASH = "$TS${MARVEL_PRIVATE_API_KEY}${MARVEL_PUBLIC_API_KEY}".md5()
}