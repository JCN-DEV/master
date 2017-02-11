'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bankSetup', {
                parent: 'entity',
                url: '/bankSetups',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.bankSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankSetup/bankSetups.html',
                        controller: 'BankSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bankSetup.detail', {
                parent: 'entity',
                url: '/bankSetup/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.bankSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankSetup/bankSetup-detail.html',
                        controller: 'BankSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BankSetup', function($stateParams, BankSetup) {
                        return BankSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bankSetup.new', {
                parent: 'bankSetup',
                url: '/new',
                data: {
                    authorities: []
                },

                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankSetup/bankSetup-dialog.html',
                        controller: 'BankSetupDialogController',
                        size: 'lg'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            code: null,
                            branchName: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankSetup/bankSetup-dialog.html',
                        controller: 'BankSetupDialogController',
                        size: 'lg',


                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    code: null,
                                    branchName: null,
                                    id: null
                                };
                            }
                        }


                    }).result.then(function(result) {
                        $state.go('bankSetup', null, { reload: true });
                    }, function() {
                        $state.go('bankSetup');
                    })
                }]*/
            })
            .state('bankSetup.edit', {
                parent: 'bankSetup',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },

                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankSetup/bankSetup-dialog.html',
                        controller: 'BankSetupDialogController',
                        size: 'lg'
                    }
                },
                resolve: {
                    entity: ['$stateParams','BankSetup', function($stateParams, BankSetup) {
                        return BankSetup.get({id : $stateParams.id});
                    }]
                }


                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankSetup/bankSetup-dialog.html',
                        controller: 'BankSetupDialogController',
                        size: 'lg',


                        resolve: {
                            entity: ['BankSetup', function(BankSetup) {
                                return BankSetup.get({id : $stateParams.id});
                            }]
                        }


                    }).result.then(function(result) {
                        $state.go('bankSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('bankSetup.delete', {
                parent: 'bankSetup',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankSetup/bankSetup-delete-dialog.html',
                        controller: 'BankSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BankSetup', function(BankSetup) {
                                return BankSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bankSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
