package com.example.android.auriculoterapia_app.constants

import com.cloudinary.Cloudinary

class ConfigCloudinary {
    companion object{
       fun config() :Map<String?,String?>{
           val config : Map<String?,String?>
           config = HashMap()
           //cloud prueba
           config.put("cloud_name","dyifsbjuf")
           config.put("api_key","365448356813786")
           config.put("api_secret","GcTi_HrLO-L1BVR4dPSPqXd646c")

           //cloud diego
           /*config.put("cloud_name","dpi1vkodf")
           config.put("api_key","764382984172427")
           config.put("api_secret","P55_yhWvPHku7HfVUa_ILCzjb9M")*/

           return config
       }
    }
}