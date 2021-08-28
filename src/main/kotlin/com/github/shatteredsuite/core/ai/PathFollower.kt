package com.github.shatteredsuite.core.ai

import com.github.shatteredsuite.core.bukkit.BlockUtil
import com.github.shatteredsuite.core.bukkit.LocationUtil
import com.github.shatteredsuite.core.math.path.Path3
import com.github.shatteredsuite.core.math.vector.Vector3
import org.bukkit.entity.Mob
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

// Some code adapted from here:
// https://github.com/UniverseCraft/DragonsOnline/blob/master/dragons-core/src/main/java/mc/dragons/core/util/PathfindingUtil.java
object PathFollower {
    fun follow(
        mob: Mob,
        path: Path3<Double>,
        speed: Double,
        plugin: JavaPlugin,
        hasPaper: Boolean,
        then: ((mob: Mob) -> Unit) = {}
    ) {
        val aware = mob.isAware
        mob.isAware = false

        val awarenessCallback: (Mob) -> Unit = { it.isAware = aware; then(mob) }

        val paperWorks = try {
            mob.pathfinder.moveTo(mob.location)
        } catch (ex: Exception) {
            false
        }

        if (hasPaper && paperWorks) {
            runPathCallback(mob, path, speed, plugin, awarenessCallback)
        } else {
            runBukkitPathCallback(mob, path, speed, plugin, awarenessCallback)
        }
    }

    private fun runPathCallback(
        mob: Mob,
        path: Path3<Double>,
        speed: Double,
        plugin: JavaPlugin,
        finalCallback: (mob: Mob) -> Unit
    ) {
        var cb = finalCallback
        for (point in path.points) {
            cb = getPointCallback(point, speed, plugin, cb)
        }
        cb.invoke(mob)
    }

    private fun runBukkitPathCallback(
        mob: Mob,
        path: Path3<Double>,
        speed: Double,
        plugin: JavaPlugin,
        finalCallback: (Mob) -> Unit
    ) {
        val newSpeed = speed / 2
        var cb = finalCallback
        for (point in path.points) {
            cb = getBukkitPointCallback(point, newSpeed, plugin, cb)
        }
    }

    private fun getBukkitPointCallback(
        point: Vector3<Double>,
        speed: Double,
        plugin: JavaPlugin,
        next: ((Mob) -> Unit)?
    ): (Mob) -> Unit {
        val loc = LocationUtil.fromVector(point)
        return {
            it.teleport(BlockUtil.getClosestGroundColumn(it.location, surface = true))
            with(it) {
                val runnable = object : BukkitRunnable() {
                    override fun run() {
                        val curr = location
                        if (isValid) {
                            if (location.distanceSquared(loc) <= 1) {
                                cancel()
                                velocity = Vector(0, 0, 0)
                                next?.invoke(it)
                            } else {
                                val dir = loc.clone().subtract(curr).toVector().setY(0).normalize().multiply(speed)
                                val to = curr.clone().add(dir)
                                val groundY = BlockUtil.getClosestGroundColumn(to, surface = true).y
                                to.y = groundY
                                velocity = dir
                                teleport(to)
                            }
                        } else {
                            cancel()
                        }
                    }
                }
                    .runTaskTimer(plugin, 0, 1)
            }
        }
    }

    private fun getPointCallback(
        point: Vector3<Double>,
        speed: Double,
        plugin: JavaPlugin,
        next: ((Mob) -> Unit)?
    ): (Mob) -> Unit {
        val loc = LocationUtil.fromVector(point)
        return { mob: Mob ->
            mob.pathfinder.moveTo(loc, speed)
            val runnable = object : BukkitRunnable() {
                override fun run() {
                    if (mob.isValid) {
                        if (mob.location.distanceSquared(loc) <= 1) {
                            next?.invoke(mob)
                            cancel()
                        }
                    } else {
                        cancel()
                    }
                }
            }
            runnable.runTaskTimer(plugin, 0, 10)
        }
    }
}