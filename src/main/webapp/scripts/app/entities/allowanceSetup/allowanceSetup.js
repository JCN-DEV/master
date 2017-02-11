'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('allowanceSetup', {
                parent: 'entity',
                url: '/allowanceSetups',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.allowanceSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/allowanceSetup/allowanceSetups.html',
                        controller: 'AllowanceSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('allowanceSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('allowanceSetup.detail', {
                parent: 'entity',
                url: '/allowanceSetup/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.allowanceSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/allowanceSetup/allowanceSetup-detail.html',
                        controller: 'AllowanceSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('allowanceSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AllowanceSetup', function($stateParams, AllowanceSetup) {
                        return AllowanceSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('allowanceSetup.new', {
                parent: 'allowanceSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@':{
                        templateUrl: 'scripts/app/entities/allowanceSetup/allowanceSetup-dialog.html',
                        controller: 'AllowanceSetupDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            status: null,
                            createBy: null,
                            createDate: null,
                            updateBy: null,
                            updateDate: null,
                            remarks: null,
                            effectiveDate: null,
                            id: null
                        };
                    }
                }
            })
            /*.state('allowanceSetup.new', {
                parent: 'allowanceSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/allowanceSetup/allowanceSetup-dialog.html',
                        controller: 'AllowanceSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    effectiveDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('allowanceSetup', null, { reload: true });
                    }, function() {
                        $state.go('allowanceSetup');
                    })
                }]
            })*/

            .state('allowanceSetup.edit', {
                parent: 'allowanceSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/allowanceSetup/allowanceSetup-dialog.html',
                        controller: 'AllowanceSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('allowanceSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AllowanceSetup', function($stateParams, AllowanceSetup) {
                        return AllowanceSetup.get({id : $stateParams.id});
                    }]
                }
            })
            /*.state('allowanceSetup.edit', {
                parent: 'allowanceSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/allowanceSetup/allowanceSetup-dialog.html',
                        controller: 'AllowanceSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AllowanceSetup', function(AllowanceSetup) {
                                return AllowanceSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('allowanceSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });*/
    });
