'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteFinancialInfo', {
                parent: 'entity',
                url: '/instituteFinancialInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteFinancialInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteFinancialInfo/instituteFinancialInfos.html',
                        controller: 'InstituteFinancialInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteFinancialInfo.detail', {
                parent: 'entity',
                url: '/instituteFinancialInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteFinancialInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteFinancialInfo/instituteFinancialInfo-detail.html',
                        controller: 'InstituteFinancialInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteFinancialInfo', function($stateParams, InstituteFinancialInfo) {
                        return InstituteFinancialInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteFinancialInfo.new', {
                parent: 'instituteFinancialInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteFinancialInfo/instituteFinancialInfo-dialog.html',
                        controller: 'InstituteFinancialInfoDialogController',
                        size: 'lg',
                        resolve: {
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
                        $state.go('instituteFinancialInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteFinancialInfo');
                    })
                }]
            })
            .state('instituteFinancialInfo.edit', {
                parent: 'instituteFinancialInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteFinancialInfo/instituteFinancialInfo-dialog.html',
                        controller: 'InstituteFinancialInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteFinancialInfo', function(InstituteFinancialInfo) {
                                return InstituteFinancialInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteFinancialInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteFinancialInfo.delete', {
                parent: 'instituteFinancialInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteFinancialInfo/instituteFinancialInfo-delete-dialog.html',
                        controller: 'InstituteFinancialInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteFinancialInfo', function(InstituteFinancialInfo) {
                                return InstituteFinancialInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteFinancialInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
