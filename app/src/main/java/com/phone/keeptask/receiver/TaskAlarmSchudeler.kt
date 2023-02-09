package com.phone.keeptask.receiver

import com.phone.keeptask.domain.model.Task

interface TaskAlarmSchudeler {
    fun taskSchudeler(task : Task)
    fun taskCancel(task: Task)
}