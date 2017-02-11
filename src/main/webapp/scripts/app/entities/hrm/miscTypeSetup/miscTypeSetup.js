'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('miscTypeSetup', {
                parent: 'hrm',
                url: '/miscTypeSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.miscTypeSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/miscTypeSetup/miscTypeSetups.html',
                        controller: 'MiscTypeSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miscTypeSetup');
                        $translatePartialLoader.addPart('miscTypeCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('miscTypeSetup.detail', {
                parent: 'hrm',
                url: '/miscTypeSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.miscTypeSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/miscTypeSetup/miscTypeSetup-detail.html',
                        controller: 'MiscTypeSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('miscTypeSetup');
                        $translatePartialLoader.addPart('miscTypeCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MiscTypeSetup', function($stateParams, MiscTypeSetup) {
                        return MiscTypeSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('miscTypeSetup.new', {
                parent: 'miscTypeSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/miscTypeSetup/miscTypeSetup-dialog.html',
                        controller: 'MiscTypeSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    typeCategory: null,
                                    typeCode: null,
                                    typeName: null,
                                    typeTitle: null,
                                    typeTitleBn: null,
                                    typeDesc: null,
                                    activeStatus: true,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('miscTypeSetup', null, { reload: true });
                    }, function() {
                        $state.go('miscTypeSetup');
                    })
                }]
            })
            .state('miscTypeSetup.edit', {
                parent: 'miscTypeSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/miscTypeSetup/miscTypeSetup-dialog.html',
                        controller: 'MiscTypeSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MiscTypeSetup', function(MiscTypeSetup) {
                                return MiscTypeSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('miscTypeSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('miscTypeSetup.delete', {
                parent: 'miscTypeSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/miscTypeSetup/miscTypeSetup-delete-dialog.html',
                        controller: 'MiscTypeSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MiscTypeSetup', function(MiscTypeSetup) {
                                return MiscTypeSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('miscTypeSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
