'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmploymentInfo', {
                parent: 'hrm',
                url: '/hrEmploymentInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmploymentInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employmentInfo/hrEmploymentInfos.html',
                        controller: 'HrEmploymentInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmploymentInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmploymentInfo.detail', {
                parent: 'hrm',
                url: '/hrEmploymentInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmploymentInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employmentInfo/hrEmploymentInfo-detail.html',
                        controller: 'HrEmploymentInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmploymentInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmploymentInfo', function($stateParams, HrEmploymentInfo) {
                        return HrEmploymentInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmploymentInfo.profile', {
                parent: 'hrEmploymentInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employmentInfo/hrEmploymentInfo-profile.html',
                        controller: 'HrEmploymentInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmploymentInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmploymentInfo.new', {
                parent: 'hrEmploymentInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employmentInfo/hrEmploymentInfo-dialog.html',
                        controller: 'HrEmploymentInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmploymentInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            presentInstitute: null,
                            joiningDate: null,
                            regularizationDate: null,
                            jobConfNoticeNo: null,
                            confirmationDate: null,
                            officeOrderNo: null,
                            officeOrderDate: null,
                            logId: null,
                            logStatus: null,
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
            .state('hrEmploymentInfo.edit', {
                parent: 'hrEmploymentInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employmentInfo/hrEmploymentInfo-dialog.html',
                        controller: 'HrEmploymentInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmploymentInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmploymentInfo', function($stateParams, HrEmploymentInfo) {
                        return HrEmploymentInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmploymentInfo.delete', {
                parent: 'hrEmploymentInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/employmentInfo/hrEmploymentInfo-delete-dialog.html',
                        controller: 'HrEmploymentInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmploymentInfo', function(HrEmploymentInfo) {
                                return HrEmploymentInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmploymentInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hrEmploymentInfo.employmentappr', {
                parent: 'hrm',
                url: '/{id}/employmentappr',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/employmentInfo/hrEmploymentInfo-approval.html',
                        controller: 'HrEmploymentInfoApprovalController',
                        size: 'md',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('hrEmploymentInfo');
                                return $translate.refresh();
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('hrm', null, { reload: true });
                        }, function() {
                            $state.go('hrm');
                        })
                }]
            });
    });
