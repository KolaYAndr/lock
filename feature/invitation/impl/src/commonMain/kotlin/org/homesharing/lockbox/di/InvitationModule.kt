package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.InvitationApi
import org.homesharing.lockbox.data.network.api.InvitationApiImpl
import org.homesharing.lockbox.data.repository.InvitationRepositoryImpl
import org.homesharing.lockbox.domain.repository.InvitationRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val invitationModule = module {
    singleOf(::InvitationApiImpl) { bind<InvitationApi>() }
    singleOf(::InvitationRepositoryImpl) { bind<InvitationRepository>() }
}
