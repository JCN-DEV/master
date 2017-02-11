'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlOnetimeAllowInfo', {
                parent: 'payroll',
                url: '/prlOnetimeAllowInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlOnetimeAllowInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/onetimeAllowInfo/prlOnetimeAllowInfos.html',
                        controller: 'PrlOnetimeAllowInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlOnetimeAllowInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlOnetimeAllowInfo.detail', {
                parent: 'payroll',
                url: '/prlOnetimeAllowInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlOnetimeAllowInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/onetimeAllowInfo/prlOnetimeAllowInfo-detail.html',
                        controller: 'PrlOnetimeAllowInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlOnetimeAllowInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlOnetimeAllowInfo', function($stateParams, PrlOnetimeAllowInfo) {
                        return PrlOnetimeAllowInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlOnetimeAllowInfo.new', {
                parent: 'prlOnetimeAllowInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/onetimeAllowInfo/prlOnetimeAllowInfo-dialog.html',
                        controller: 'PrlOnetimeAllowInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlOnetimeAllowInfo');
                        $translatePartialLoader.addPart('religions');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            year: null,
                            basicAmountPercentage: null,
                            effectiveDate: null,
                            religion: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlOnetimeAllowInfo.edit', {
                parent: 'prlOnetimeAllowInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/onetimeAllowInfo/prlOnetimeAllowInfo-dialog.html',
                        controller: 'PrlOnetimeAllowInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlOnetimeAllowInfo');
                        $translatePartialLoader.addPart('religions');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlOnetimeAllowInfo', function($stateParams, PrlOnetimeAllowInfo) {
                        return PrlOnetimeAllowInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlOnetimeAllowInfo.delete', {
                parent: 'prlOnetimeAllowInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/onetimeAllowInfo/prlOnetimeAllowInfo-delete-dialog.html',
                        controller: 'PrlOnetimeAllowInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlOnetimeAllowInfo', function(PrlOnetimeAllowInfo) {
                                return PrlOnetimeAllowInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlOnetimeAllowInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
