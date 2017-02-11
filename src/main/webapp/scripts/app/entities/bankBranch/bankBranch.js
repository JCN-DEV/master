'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bankBranch', {
                parent: 'entity',
                url: '/bankBranchs',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.bankBranch.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankBranch/bank-branch-content-info.html',
                        controller: 'BankBranchController'
                    },
                     'bankBranchView@bankBranch':{
                      templateUrl: 'scripts/app/entities/bankBranch/bankBranchs.html',
                      controller: 'BankBranchController'
                     }
                },


                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankBranch');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bankBranch.detail', {
                parent: 'bankBranch',
                url: '/bankBranch/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.bankBranch.detail.title'
                },
                views: {
                    'bankBranchView@bankBranch': {
                        templateUrl: 'scripts/app/entities/bankBranch/bankBranch-detail.html',
                        controller: 'BankBranchDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankBranch');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BankBranch', function($stateParams, BankBranch) {
                        return BankBranch.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bankBranch.new', {
                parent: 'bankBranch',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                 views: {
                        'bankBranchView@bankBranch': {
                           templateUrl: 'scripts/app/entities/bankBranch/bankBranch-dialog.html',
                           controller: 'BankBranchDialogController'
                        }
                                },
                  resolve: {
                        entity: function () {
                            return {
                                brName: null,
                                address: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankBranch/bankBranch-dialog.html',
                        controller: 'BankBranchDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    brName: null,
                                    address: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bankBranch', null, { reload: true });
                    }, function() {
                        $state.go('bankBranch');
                    })
                }]*/
            })
            .state('bankBranch.edit', {
                parent: 'bankBranch',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'bankBranchView@bankBranch': {
                        templateUrl: 'scripts/app/entities/bankBranch/bankBranch-dialog.html',
                        controller: 'BankBranchDialogController'
                    }
                },
                resolve: {
                    entity: ['BankBranch', '$stateParams',function(BankBranch,$stateParams) {
                        return BankBranch.get({id : $stateParams.id});
                    }]
                }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/bankBranch/bankBranch-dialog.html',
                //        controller: 'BankBranchDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['BankBranch', function(BankBranch) {
                //                return BankBranch.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('bankBranch', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            })
            .state('bankBranch.delete', {
                parent: 'bankBranch',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bankBranch/bankBranch-delete-dialog.html',
                        controller: 'BankBranchDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BankBranch', function(BankBranch) {
                                return BankBranch.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bankBranch', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
