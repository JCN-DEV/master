'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('incrementSetup', {
                parent: 'entity',
                url: '/incrementSetups',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.incrementSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/incrementSetup/incrementSetups.html',
                        controller: 'IncrementSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('incrementSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('incrementSetup.detail', {
                parent: 'entity',
                url: '/incrementSetup/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.incrementSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/incrementSetup/incrementSetup-detail.html',
                        controller: 'IncrementSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('incrementSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IncrementSetup', function($stateParams, IncrementSetup) {
                        return IncrementSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('incrementSetup.new', {
                parent: 'incrementSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN']
                },
                views: {
                    'content@':{
                        templateUrl: 'scripts/app/entities/incrementSetup/incrementSetup-dialog.html',
                        controller: 'IncrementSetupDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            amount: null,
                            effectiveDate: null,
                            stopDate: null,
                            status: null,
                            createBy: null,
                            createDate: null,
                            updateBy: null,
                            updateDate: null,
                            remarks: null,
                            id: null
                        };
                    }
                }
            })
            .state('incrementSetup.edit', {
                parent: 'incrementSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/incrementSetup/incrementSetup-dialog.html',
                        controller: 'IncrementSetupDialogController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'IncrementSetup', function ($stateParams, IncrementSetup) {
                        return IncrementSetup.get({id: $stateParams.id});
                    }]
                }
            })
            /*.state('incrementSetup.new', {
                parent: 'incrementSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/incrementSetup/incrementSetup-dialog.html',
                        controller: 'IncrementSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    amount: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    PayCodeSerial: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('incrementSetup', null, { reload: true });
                    }, function() {
                        $state.go('incrementSetup');
                    })
                }]
            })
            .state('incrementSetup.edit', {
                parent: 'incrementSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/incrementSetup/incrementSetup-dialog.html',
                        controller: 'IncrementSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IncrementSetup', function(IncrementSetup) {
                                return IncrementSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('incrementSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            ;
    });
