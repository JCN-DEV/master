'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplBankInfo', {
                parent: 'entity',
                url: '/instEmplBankInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplBankInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplBankInfo/instEmplBankInfos.html',
                        controller: 'InstEmplBankInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplBankInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplBankInfo.detail', {
                parent: 'entity',
                url: '/instEmplBankInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplBankInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplBankInfo/instEmplBankInfo-detail.html',
                        controller: 'InstEmplBankInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplBankInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplBankInfo', function($stateParams, InstEmplBankInfo) {
                        return InstEmplBankInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplBankInfo.new', {
                parent: 'instEmplBankInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplBankInfo/instEmplBankInfo-dialog.html',
                        controller: 'InstEmplBankInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    bankName: null,
                                    bankBranch: null,
                                    bankAccountNo: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplBankInfo', null, { reload: true });
                    }, function() {
                        $state.go('instEmplBankInfo');
                    })
                }]
            })
            .state('instEmplBankInfo.edit', {
                parent: 'instEmplBankInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplBankInfo/instEmplBankInfo-dialog.html',
                        controller: 'InstEmplBankInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplBankInfo', function(InstEmplBankInfo) {
                                return InstEmplBankInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplBankInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplBankInfo.delete', {
                parent: 'instEmplBankInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplBankInfo/instEmplBankInfo-delete-dialog.html',
                        controller: 'InstEmplBankInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplBankInfo', function(InstEmplBankInfo) {
                                return InstEmplBankInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplBankInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
