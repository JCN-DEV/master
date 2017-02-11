'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instFinancialInfoTemp', {
                parent: 'entity',
                url: '/instFinancialInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instFinancialInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instFinancialInfoTemp/instFinancialInfoTemps.html',
                        controller: 'InstFinancialInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfoTemp');
                        $translatePartialLoader.addPart('accountType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instFinancialInfoTemp.detail', {
                parent: 'entity',
                url: '/instFinancialInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instFinancialInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instFinancialInfoTemp/instFinancialInfoTemp-detail.html',
                        controller: 'InstFinancialInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfoTemp');
                        $translatePartialLoader.addPart('accountType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstFinancialInfoTemp', function($stateParams, InstFinancialInfoTemp) {
                        return InstFinancialInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instFinancialInfoTemp.new', {
                parent: 'instFinancialInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfoTemp/instFinancialInfoTemp-dialog.html',
                        controller: 'InstFinancialInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    bankName: null,
                                    branchName: null,
                                    accountType: null,
                                    accountNo: null,
                                    issueDate: null,
                                    createDate: null,
                                    updateDate: null,
                                    expireDate: null,
                                    amount: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instFinancialInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('instFinancialInfoTemp');
                    })
                }]
            })
            .state('instFinancialInfoTemp.edit', {
                parent: 'instFinancialInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfoTemp/instFinancialInfoTemp-dialog.html',
                        controller: 'InstFinancialInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstFinancialInfoTemp', function(InstFinancialInfoTemp) {
                                return InstFinancialInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instFinancialInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instFinancialInfoTemp.delete', {
                parent: 'instFinancialInfoTemp',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instFinancialInfoTemp/instFinancialInfoTemp-delete-dialog.html',
                        controller: 'InstFinancialInfoTempDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstFinancialInfoTemp', function(InstFinancialInfoTemp) {
                                return InstFinancialInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instFinancialInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
