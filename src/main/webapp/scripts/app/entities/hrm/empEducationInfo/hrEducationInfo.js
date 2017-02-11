'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEducationInfo', {
                parent: 'hrm',
                url: '/hrEducationInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEducationInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empEducationInfo/hrEducationInfos.html',
                        controller: 'HrEducationInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEducationInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEducationInfo.detail', {
                parent: 'hrm',
                url: '/hrEducationInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEducationInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empEducationInfo/hrEducationInfo-detail.html',
                        controller: 'HrEducationInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEducationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEducationInfo', function($stateParams, HrEducationInfo) {
                        return HrEducationInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEducationInfo.new', {
                parent: 'hrEducationInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empEducationInfo/hrEducationInfo-dialog.html',
                        controller: 'HrEducationInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEducationInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            examTitle: null,
                            majorSubject: null,
                            certSlNumber: null,
                            sessionYear: null,
                            rollNumber: null,
                            instituteName: null,
                            gpaOrCgpa: null,
                            gpaScale: null,
                            passingYear: null,
                            certificateDoc: null,
                            certificateDocContentType: null,
                            certificateDocName: null,
                            transcriptDoc: null,
                            transcriptDocContentType: null,
                            transcriptDocName: null,
                            activeStatus: true,
                            logId:null,
                            logStatus:null,
                            logComments:null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEducationInfo.profile', {
                parent: 'hrEducationInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empEducationInfo/hrEducationInfo-profile.html',
                        controller: 'HrEducationInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEducationInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            examTitle: null,
                            majorSubject: null,
                            certSlNumber: null,
                            sessionYear: null,
                            rollNumber: null,
                            instituteName: null,
                            gpaOrCgpa: null,
                            gpaScale: null,
                            passingYear: null,
                            certificateDoc: null,
                            certificateDocContentType: null,
                            certificateDocName: null,
                            transcriptDoc: null,
                            transcriptDocContentType: null,
                            transcriptDocName: null,
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
            .state('hrEducationInfo.edit', {
                parent: 'hrEducationInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },

                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empEducationInfo/hrEducationInfo-dialog.html',
                        controller: 'HrEducationInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEducationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEducationInfo', function($stateParams, HrEducationInfo) {
                        return HrEducationInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEducationInfo.delete', {
                parent: 'hrEducationInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empEducationInfo/hrEducationInfo-delete-dialog.html',
                        controller: 'HrEducationInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEducationInfo', function(HrEducationInfo) {
                                return HrEducationInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEducationInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
