'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bankAssign', {
                parent: 'entity',
                url: '/bankAssigns',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.bankAssign.home.title'
                },
                views: {

                    'content@': {
                        templateUrl: 'scripts/app/entities/bankBranch/bank-branch-content-info.html',
                        controller: 'BankBranchController'
                    },
                    'bankBranchView@bankAssign':{
                        templateUrl: 'scripts/app/entities/bankAssign/bankAssigns.html',
                        controller: 'BankAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankAssign');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('bankBranch');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bankAssign.detail', {
                parent: 'entity',
                url: '/bankAssign/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.bankAssign.detail.title'
                },
                views: {
                    'bankBranchView@bankAssign': {
                        templateUrl: 'scripts/app/entities/bankAssign/bankAssign-detail.html',
                        controller: 'BankAssignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BankAssign', function($stateParams, BankAssign) {
                        return BankAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bankAssign.new', {
                parent: 'bankAssign',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'bankBranchView@bankAssign': {
                        templateUrl: 'scripts/app/entities/bankAssign/bankAssign-dialog.html',
                        controller: 'BankAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BankAssign', function($stateParams, BankAssign) {
                        return BankAssign.get({id : $stateParams.id});
                    }]
                }
            })
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankAssign/bankAssign-dialog.html',
                        controller: 'BankAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    createdDate: null,
                                    modifiedDate: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bankAssign', null, { reload: true });
                    }, function() {
                        $state.go('bankAssign');
                    })
                }]
            })*/
            .state('bankAssign.edit', {
                parent: 'bankAssign',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'bankBranchView@bankAssign': {
                        templateUrl: 'scripts/app/entities/bankAssign/bankAssign-dialog.html',
                        controller: 'BankAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BankAssign', function($stateParams, BankAssign) {
                        return BankAssign.get({id : $stateParams.id});
                    }]
                }
            })
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankAssign/bankAssign-dialog.html',
                        controller: 'BankAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BankAssign', function(BankAssign) {
                                return BankAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bankAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            .state('bankAssign.delete', {
                parent: 'bankAssign',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankAssign/bankAssign-delete-dialog.html',
                        controller: 'BankAssignDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BankAssign', function(BankAssign) {
                                return BankAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bankAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
