package com.yourgains.mvpmoxydaggertemplate.data.network.error

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class VariableError(
    @SerializedName("fields") var fields: CommonErrorList? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("name") var name: String? = null
)

class FieldsDeserializer : JsonDeserializer<VariableError> {

    companion object {
        private const val FIELDS = "fields"
    }

    val gson: Gson by lazy {
        GsonBuilder().registerTypeAdapter(
            VariableError::class.java,
            FieldsDeserializer()
        ).create()
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): VariableError {

        val errorList =
            CommonErrorList()
        var variableError =
            VariableError(
                errorList
            )
        json?.asJsonObject?.also { data ->
            data.keySet()?.map { key ->
                when (data[key]) {
                    is JsonObject -> {
                        takeIf { key != FIELDS }?.let {
                            parseJsonObjectList(data[key] as JsonObject)?.let {
                                it.name = key
                                errorList.variableError.add(it)
                            }
                        } ?: let {
                            parseJsonObjectSingle(data[key] as JsonObject)?.let {
                                it.name = key
                                variableError = deserialize(data[key], typeOfT, context)
                                variableError.name = key
                            }
                        }
                    }
                    is JsonArray -> errorList.errors = parseJsonArray(data[key] as JsonArray)
                    is JsonPrimitive -> {
                        variableError.name = key
                        variableError.message = data[key].asString
                    }
                    else -> Unit
                }
            }
        }
        return variableError
    }

    private fun parseJsonObjectSingle(jsonObj: JsonObject): CommonErrorList? =
        gson.fromJson(jsonObj, CommonErrorList::class.java)

    private fun parseJsonObjectList(jsonObj: JsonObject): VariableError? =
        gson.fromJson(jsonObj, VariableError::class.java)

    private fun parseJsonArray(jsonArray: JsonArray): List<ErrorDetails> =
        gson.fromJson(jsonArray, object : TypeToken<List<ErrorDetails>>() {}.type)

}