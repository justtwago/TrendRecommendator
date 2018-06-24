package com.example.artyomvlasov.trendrecommendator.util


object ClothesTypeManager {
    private val clothesMap: HashMap<String, String> = hashMapOf(
            "obuwie damskie" to "shoes",
            "obuwie meskie" to "shoes",
            "odziez damska bluzki tuniki" to "blouse",
            "odziez damska jeansy" to "jeans",
            "odziez damska koszulki" to "shirt",
            "odziez damska kurtki" to "jacket",
            "odziez damska spodnice" to "skirt",
            "odziez damska spodnie" to "trousers",
            "odziez damska sukienki" to "dress",
            "odziez damska swetry bluzy" to "sweater/hoodie",
            "odziez meska bluzy kardigany" to "cardigan",
            "odziez meska garnitury" to "suit",
            "odziez meska jeansy" to "jeans",
            "odziez meska koszule" to "shirt",
            "odziez meska koszulki" to "t-shirt",
            "odziez meska kurtki" to "jacket",
            "odziez meska kurtki wiosenne" to "jacket",
            "odziez meska plaszcze" to "coat",
            "odziez meska spodnie" to "trousers"
    )

    fun getClothesName(key: String) = clothesMap.getValue(key)
}