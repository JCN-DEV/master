'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instFinancialInfo', {
                parent: 'entity',
                url: '/instFinancialInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instFinancialInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfos.html',
                        controller: 'InstFinancialInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('instGenInfo.instFinancialInfo.delete', {
                parent: 'instGenInfo',
                url: '/{id}/delete-Financial-Info',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfo-delete-dialog.html',
                        controller: 'InstFinancialInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstFinancialInfo', function(InstFinancialInfo) {
                                return InstFinancialInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('instGenInfo.instFinancialInfo', null, { reload: true });
                        }, function() {
                            $state.go('instGenInfo.instFinancialInfo');
                        })
                }]

            }).state('instGenInfo.instFinancialInfo.detail', {
                parent: 'instGenInfo',
                url: '/inst-Financial-Info/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instFinancialInfo.detail.title'
                },
                views: {
                    'instituteView@instGenInfo': {
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfo-detail.html',
                        controller: 'InstFinancialInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstFinancialInfo', function($stateParams, InstFinancialInfo) {
                        return InstFinancialInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGenInfo.instFinancialInfo.new', {
                parent: 'instGenInfo',
                url: '/new-Financial-Info',
                data: {
                   // authorities: ['ROLE_USER'],
                    authorities: [],
                },views: {
                    'instituteView@instGenInfo': {
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfo-dialog.html',
                        controller: 'InstFinancialInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            bankName: null,
                            branchName: null,
                            accountType: null,
                            accountNo: null,
                            issueDate: null,
                            expireDate: null,
                            amount: null,
                            status: null,
                            id: null
                        };
                    }
                }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfo-dialog.html',
                        controller: 'InstFinancialInfoDialogController',
                        size: 'lg',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('instFinancialInfo');
                                $translatePartialLoader.addPart('accountType');
                                return $translate.refresh();
                            }],
                            entity: function () {
                                return {
                                    bankName: null,
                                    branchName: null,
                                    accountType: null,
                                    accountNo: null,
                                    issueDate: null,
                                    expireDate: null,
                                    amount: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.instFinancialInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.instFinancialInfo');
                    })
                }]*/
            })
            .state('instGenInfo.instFinancialInfo.edit', {
                parent: 'instGenInfo',
                url: '/{id}/edit-Financial-Info',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfo-dialog.html',
                        controller: 'InstFinancialInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstFinancialInfo', function(InstFinancialInfo) {
                                return InstFinancialInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.instFinancialInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.instFinancialInfo');
                    })
                }]
            })
            .state('instGenInfo.instFinancialInfo.approve', {
                parent: 'instGenInfo',
                url: '/{id}/approve-Inst-Financial-Info',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfo-approve-dialog.html',
                        controller: 'InstFinancialInfoApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['InstFinancialInfo', function(InstFinancialInfo) {
                                return InstFinancialInfo.get({id : $stateParams.id});
                                //  return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instGenInfo.instFinancialInfo', null, { reload: true });
                        }, function() {
                            $state.go('instGenInfo.instFinancialInfo');
                        })

                }]

            });
    });
