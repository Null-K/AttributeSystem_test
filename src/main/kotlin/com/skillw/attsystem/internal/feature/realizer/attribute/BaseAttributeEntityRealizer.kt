package com.skillw.attsystem.internal.feature.realizer.attribute

import com.skillw.attsystem.AttributeSystem
import com.skillw.attsystem.api.AttrAPI.read
import com.skillw.attsystem.api.compiled.CompiledAttrDataCompound
import com.skillw.attsystem.api.compiled.CompiledData
import com.skillw.attsystem.api.compiled.sub.ComplexCompiledData
import com.skillw.attsystem.api.realizer.BaseRealizer
import com.skillw.attsystem.api.realizer.component.Awakeable
import com.skillw.pouvoir.api.plugin.annotation.AutoRegister
import taboolib.common.util.asList
import taboolib.module.configuration.util.asMap

/**
 * @className BaseAttributePlayerRealizer
 *
 * Ӧ�ý��� basic attribute�ġ���
 *
 * @author Glom
 * @date 2023/1/6 7:05 Copyright 2022 user. All rights reserved.
 */
@AutoRegister
internal object BaseAttributeEntityRealizer : BaseRealizer("base-attribute-entity"), Awakeable {

    override val file by lazy {
        AttributeSystem.options.file!!
    }
    val type
        get() = config["type"]?.toString()?.lowercase() ?: "strings"
    val attrData
        get() = config["attributes"]
    val conditions
        get() = config["conditions"]

    private const val KEY = "BASIC-ATTRIBUTE"

    private var compiledBaseData: CompiledData = ComplexCompiledData()


    override fun onEnable() {
        onReload()
    }

    override fun onReload() {
        val base = when (type) {
            "nbt" -> {
                val attrData = attrData.asMap().entries.associate { it.key to it.value!! }.toMutableMap()
                val conditions = conditions as? List<Any>? ?: emptyList()
                AttributeSystem.readManager.readMap(attrData, conditions)
            }

            else -> attrData?.asList()?.read()
        }
        base?.let {
            compiledBaseData = it
        }
    }

    fun CompiledAttrDataCompound.baseEntity(): CompiledAttrDataCompound {
        this[KEY] = compiledBaseData
        return this
    }


}