'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsUtmostGpfApp', {
                parent: 'hrm',
                url: '/pfmsUtmostGpfApps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsUtmostGpfApp.home.title',
                    displayName: 'Utmost Gpf Application'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsUtmostGpfApp/pfmsUtmostGpfApps.html',
                        controller: 'PfmsUtmostGpfAppController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsUtmostGpfApp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsUtmostGpfApp.detail', {
                parent: 'hrm',
                url: '/pfmsUtmostGpfApp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsUtmostGpfApp.detail.title',
                    displayName: 'Utmost Gpf Application Details'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsUtmostGpfApp/pfmsUtmostGpfApp-detail.html',
                        controller: 'PfmsUtmostGpfAppDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsUtmostGpfApp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsUtmostGpfApp', function($stateParams, PfmsUtmostGpfApp) {
                        return PfmsUtmostGpfApp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsUtmostGpfApp.new', {
                parent: 'pfmsUtmostGpfApp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Add Utmost Gpf Application'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsUtmostGpfApp/pfmsUtmostGpfApp-dialog.html',
                        controller: 'PfmsUtmostGpfAppDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsUtmostGpfApp');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            prlDate: null,
                            gpfNo: null,
                            lastDeduction: null,
                            deductionDate: null,
                            billNo: null,
                            billDate: null,
                            tokenNo: null,
                            tokenDate: null,
                            withdrawFrom: null,
                            applyDate: null
                        };
                    }
                }
            })
            .state('pfmsUtmostGpfApp.edit', {
                parent: 'pfmsUtmostGpfApp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Utmost Gpf Application'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsUtmostGpfApp/pfmsUtmostGpfApp-dialog.html',
                        controller: 'PfmsUtmostGpfAppDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsUtmostGpfApp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsUtmostGpfApp', function($stateParams, PfmsUtmostGpfApp) {
                        return PfmsUtmostGpfApp.get({id : $stateParams.id});
                    }]
                }
            }).state('pfmsUtmostAppPending', {
                parent: 'pfms',
                url: '/pfmsUtmostApplicationPending',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsUtmostGpfApp.home.title',
                    displayName: 'Utmost Gpf Application'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsUtmostGpfApp/pfmsPendinUtmostApp.html',
                        controller: 'PfmsUtmostGpfAppLController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsUtmostGpfApp');
                        $translatePartialLoader.addPart('approvalStatus');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrmHome');
                        return $translate.refresh();
                    }]
                }
            });
    });
