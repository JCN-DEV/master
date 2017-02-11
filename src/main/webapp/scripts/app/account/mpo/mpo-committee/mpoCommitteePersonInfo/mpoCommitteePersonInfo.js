/*
'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            */
/*.state('mpoCommitteePersonInfo', {
                parent: 'entity',
                url: '/mpoCommitteePersonInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfos.html',
                        controller: 'MpoCommitteePersonInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoCommitteePersonInfo.detail', {
                parent: 'entity',
                url: '/mpoCommitteePersonInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfo-detail.html',
                        controller: 'MpoCommitteePersonInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteePersonInfo', function($stateParams, MpoCommitteePersonInfo) {
                        return MpoCommitteePersonInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoCommitteePersonInfo.new', {
                parent: 'mpoCommitteePersonInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfo-dialog.html',
                        controller: 'MpoCommitteePersonInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    contactNo: null,
                                    address: null,
                                    designation: null,
                                    orgName: null,
                                    dateCrated: null,
                                    dateModified: null,
                                    status: null,
                                    activated: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteePersonInfo', null, { reload: true });
                    }, function() {
                        $state.go('mpoCommitteePersonInfo');
                    })
                }]
            })
            .state('mpoCommitteePersonInfo.edit', {
                parent: 'mpoCommitteePersonInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfo-dialog.html',
                        controller: 'MpoCommitteePersonInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoCommitteePersonInfo', function(MpoCommitteePersonInfo) {
                                return MpoCommitteePersonInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteePersonInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mpoCommitteePersonInfo.delete', {
                parent: 'mpoCommitteePersonInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfo-delete-dialog.html',
                        controller: 'MpoCommitteePersonInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoCommitteePersonInfo', function(MpoCommitteePersonInfo) {
                                return MpoCommitteePersonInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteePersonInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    })*//*
;
*/
