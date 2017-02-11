'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrDesignationHeadSetup', {
                parent: 'hrm',
                url: '/hrDesignationHeadSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDesignationHeadSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationHeadSetup/hrDesignationHeadSetups.html',
                        controller: 'HrDesignationHeadSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationHeadSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrDesignationHeadSetup.detail', {
                parent: 'hrm',
                url: '/hrDesignationHeadSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDesignationHeadSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationHeadSetup/hrDesignationHeadSetup-detail.html',
                        controller: 'HrDesignationHeadSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationHeadSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDesignationHeadSetup', function($stateParams, HrDesignationHeadSetup) {
                        return HrDesignationHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDesignationHeadSetup.new', {
                parent: 'hrDesignationHeadSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationHeadSetup/hrDesignationHeadSetup-dialog.html',
                        controller: 'HrDesignationHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationHeadSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            designationCode: null,
                            designationName: null,
                            designationDetail: null,
                            designationLevel:null,
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
            .state('hrDesignationHeadSetup.edit', {
                parent: 'hrDesignationHeadSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationHeadSetup/hrDesignationHeadSetup-dialog.html',
                        controller: 'HrDesignationHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationHeadSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDesignationHeadSetup', function($stateParams, HrDesignationHeadSetup) {
                        return HrDesignationHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDesignationHeadSetup.delete', {
                parent: 'hrDesignationHeadSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/designationHeadSetup/hrDesignationHeadSetup-delete-dialog.html',
                        controller: 'HrDesignationHeadSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrDesignationHeadSetup', function(HrDesignationHeadSetup) {
                                return HrDesignationHeadSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrDesignationHeadSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
