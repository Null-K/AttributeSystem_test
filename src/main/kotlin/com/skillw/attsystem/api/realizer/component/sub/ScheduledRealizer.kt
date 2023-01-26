package com.skillw.attsystem.api.realizer.component.sub

import com.skillw.attsystem.api.realizer.BaseRealizer
import taboolib.common.platform.function.submit
import taboolib.common.platform.service.PlatformExecutor

/**
 * @className Realizable
 *
 * @author Glom
 * @date 2023/1/5 16:25 Copyright 2022 user. All rights reserved.
 */
abstract class ScheduledRealizer(key: String, val async: Boolean = false) : BaseRealizer(key), Awakeable {
    private var task:
            PlatformExecutor.PlatformTask? = null

    abstract val defaultPeriod: Long
    private fun period(): Long {
        return config.getOrDefault("period", defaultPeriod).toString().toLong()
    }

    abstract fun task()

    protected fun refreshTask() {
        cancelTask()
        task = submit(period = period(), async = async) {
            task()
        }
    }

    protected fun cancelTask() {
        task?.cancel()
    }

    override fun onEnable() {
        refreshTask()
    }

    override fun onReload() {
        refreshTask()
    }

    override fun onDisable() {
        cancelTask()
    }

    companion object {
        @JvmStatic
        fun defaultConfig(realizer: ScheduledRealizer, config: MutableMap<String, Any>) {
            config["period"] = realizer.defaultPeriod
        }
    }
}