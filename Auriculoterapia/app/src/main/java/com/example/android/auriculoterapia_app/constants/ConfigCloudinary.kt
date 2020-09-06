package com.example.android.auriculoterapia_app.constants

import com.cloudinary.Cloudinary

class ConfigCloudinary {
    companion object{
       fun config() :Map<String?,String?>{
           val config : Map<String?,String?>
           config = HashMap()

           config.put("cloud_name","dyifsbjuf")
           config.put("api_key","365448356813786")
           config.put("api_secret","GcTi_HrLO-L1BVR4dPSPqXd646c")

           return config
       }
    }
}