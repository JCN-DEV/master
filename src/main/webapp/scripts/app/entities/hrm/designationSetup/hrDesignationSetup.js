'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrDesignationSetup', {
                parent: 'hrm',
                url: '/hrDesignationSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDesignationSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationSetup/hrDesignationSetups.html',
                        controller: 'HrDesignationSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationSetup');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrDesignationSetup.detail', {
                parent: 'hrm',
                url: '/hrDesignationSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDesignationSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationSetup/hrDesignationSetup-detail.html',
                        controller: 'HrDesignationSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDesignationSetup', function($stateParams, HrDesignationSetup) {
                        return HrDesignationSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDesignationSetup.new', {
                parent: 'hrDesignationSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationSetup/hrDesignationSetup-dialog.html',
                        controller: 'HrDesignationSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            elocattedPosition: null,
                            desigType: "HRM",
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
            .state('hrDesignationSetup.edit', {
                parent: 'hrDesignationSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationSetup/hrDesignationSetup-dialog.html',
                        controller: 'HrDesignationSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDesignationSetup', function($stateParams, HrDesignationSetup) {
                        return HrDesignationSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDesignationSetup.newmpo', {
                parent: 'hrDesignationSetup',
                url: '/newmpo',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationSetup/hrDesignationSetup-mpo.html',
                        controller: 'HrDesignationSetupMpoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            elocattedPosition: null,
                            desigType: "",
                            organizationType:"Institute",
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
            .state('hrDesignationSetup.editmpo', {
                parent: 'hrDesignationSetup',
                url: '/{id}/editmpo',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/designationSetup/hrDesignationSetup-mpo.html',
                        controller: 'HrDesignationSetupMpoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDesignationSetup');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDesignationSetup', function($stateParams, HrDesignationSetup) {
                        return HrDesignationSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDesignationSetup.delete', {
                parent: 'hrDesignationSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/designationSetup/hrDesignationSetup-delete-dialog.html',
                        controller: 'HrDesignationSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrDesignationSetup', function(HrDesignationSetup) {
                                return HrDesignationSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrDesignationSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
