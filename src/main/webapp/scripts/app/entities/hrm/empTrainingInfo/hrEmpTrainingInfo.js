'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpTrainingInfo', {
                parent: 'hrm',
                url: '/hrEmpTrainingInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpTrainingInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTrainingInfo/hrEmpTrainingInfos.html',
                        controller: 'HrEmpTrainingInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTrainingInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpTrainingInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpTrainingInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpTrainingInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTrainingInfo/hrEmpTrainingInfo-detail.html',
                        controller: 'HrEmpTrainingInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTrainingInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpTrainingInfo', function($stateParams, HrEmpTrainingInfo) {
                        return HrEmpTrainingInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpTrainingInfo.new', {
                parent: 'hrEmpTrainingInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTrainingInfo/hrEmpTrainingInfo-dialog.html',
                        controller: 'HrEmpTrainingInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTrainingInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            instituteName: null,
                            courseTitle: null,
                            durationFrom: null,
                            durationTo: null,
                            result: null,
                            officeOrderNo: null,
                            jobCategory: null,
                            country: null,
                            goOrderDoc: null,
                            goOrderDocContentType: null,
                            goOrderDocName: null,
                            certDoc: null,
                            certDocContentType: null,
                            certDocName: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
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
            .state('hrEmpTrainingInfo.profile', {
                parent: 'hrEmpTrainingInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTrainingInfo/hrEmpTrainingInfo-profile.html',
                        controller: 'HrEmpTrainingInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTrainingInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            instituteName: null,
                            courseTitle: null,
                            durationFrom: null,
                            durationTo: null,
                            result: null,
                            officeOrderNo: null,
                            jobCategory: null,
                            country: null,
                            goOrderDoc: null,
                            goOrderDocContentType: null,
                            goOrderDocName: null,
                            certDoc: null,
                            certDocContentType: null,
                            certDocName: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpTrainingInfo.edit', {
                parent: 'hrEmpTrainingInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTrainingInfo/hrEmpTrainingInfo-dialog.html',
                        controller: 'HrEmpTrainingInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTrainingInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpTrainingInfo', function($stateParams, HrEmpTrainingInfo) {
                        return HrEmpTrainingInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpTrainingInfo.delete', {
                parent: 'hrEmpTrainingInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empTrainingInfo/hrEmpTrainingInfo-delete-dialog.html',
                        controller: 'HrEmpTrainingInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpTrainingInfo', function(HrEmpTrainingInfo) {
                                return HrEmpTrainingInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpTrainingInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
