'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpAwardInfo', {
                parent: 'hrm',
                url: '/hrEmpAwardInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAwardInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAwardInfo/hrEmpAwardInfos.html',
                        controller: 'HrEmpAwardInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAwardInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAwardInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpAwardInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAwardInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAwardInfo/hrEmpAwardInfo-detail.html',
                        controller: 'HrEmpAwardInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAwardInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAwardInfo', function($stateParams, HrEmpAwardInfo) {
                        return HrEmpAwardInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAwardInfo.new', {
                parent: 'hrEmpAwardInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAwardInfo/hrEmpAwardInfo-dialog.html',
                        controller: 'HrEmpAwardInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAwardInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            awardName: null,
                            awardArea: null,
                            awardDate: null,
                            remarks: null,
                            certNumber:null,
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
            .state('hrEmpAwardInfo.profile', {
                parent: 'hrEmpAwardInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAwardInfo/hrEmpAwardInfo-profile.html',
                        controller: 'HrEmpAwardInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAwardInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAwardInfo.edit', {
                parent: 'hrEmpAwardInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAwardInfo/hrEmpAwardInfo-dialog.html',
                        controller: 'HrEmpAwardInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAwardInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAwardInfo', function($stateParams, HrEmpAwardInfo) {
                        return HrEmpAwardInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAwardInfo.delete', {
                parent: 'hrEmpAwardInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empAwardInfo/hrEmpAwardInfo-delete-dialog.html',
                        controller: 'HrEmpAwardInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpAwardInfo', function(HrEmpAwardInfo) {
                                return HrEmpAwardInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpAwardInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
