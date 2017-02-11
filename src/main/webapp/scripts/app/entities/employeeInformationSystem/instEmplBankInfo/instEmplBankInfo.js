'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instEmplBankInfo', {
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
            })*/
            .state('employeeInfo.bankInfo', {
                parent: 'employeeInfo',
                url: '/bank-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmplBankInfo.detail.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplBankInfo/instEmplBankInfo-detail.html',
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
            .state('employeeInfo.bankInfo.new', {
                parent: 'employeeInfo.bankInfo',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                             templateUrl: 'scripts/app/entities/instEmplBankInfo/instEmplBankInfo-dialog.html',
                             controller: 'InstEmplBankInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplBankInfo');
                        return $translate.refresh();
                    }],
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
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            })
            .state('employeeInfo.bankInfo.edit', {
                parent: 'employeeInfo.bankInfo',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                             templateUrl: 'scripts/app/entities/instEmplBankInfo/instEmplBankInfo-dialog.html',
                             controller: 'InstEmplBankInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplBankInfo');
                        return $translate.refresh();
                    }],
                    entity: ['InstEmplBankInfo','$stateParams', function(InstEmplBankInfo,$stateParams) {
                        return InstEmplBankInfo.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            })
            .state('employeeInfo.bankInfo.delete', {
                parent: 'employeeInfo.bankInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            });
    });
