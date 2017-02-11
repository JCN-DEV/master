'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrGradeSetup', {
                parent: 'hrm',
                url: '/hrGradeSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrGradeSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gradeSetup/hrGradeSetups.html',
                        controller: 'HrGradeSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGradeSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrGradeSetup.detail', {
                parent: 'hrm',
                url: '/hrGradeSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrGradeSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gradeSetup/hrGradeSetup-detail.html',
                        controller: 'HrGradeSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGradeSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrGradeSetup', function($stateParams, HrGradeSetup) {
                        return HrGradeSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrGradeSetup.new', {
                parent: 'hrGradeSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gradeSetup/hrGradeSetup-dialog.html',
                        controller: 'HrGradeSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGradeSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            gradeCode: null,
                            gradeDetail: null,
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
            .state('hrGradeSetup.edit', {
                parent: 'hrGradeSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },

                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gradeSetup/hrGradeSetup-dialog.html',
                        controller: 'HrGradeSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGradeSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrGradeSetup', function($stateParams, HrGradeSetup) {
                        return HrGradeSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrGradeSetup.delete', {
                parent: 'hrGradeSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/gradeSetup/hrGradeSetup-delete-dialog.html',
                        controller: 'HrGradeSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrGradeSetup', function(HrGradeSetup) {
                                return HrGradeSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrGradeSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
