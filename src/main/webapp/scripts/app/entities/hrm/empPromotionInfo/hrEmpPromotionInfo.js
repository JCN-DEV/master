'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpPromotionInfo', {
                parent: 'hrm',
                url: '/hrEmpPromotionInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpPromotionInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPromotionInfo/hrEmpPromotionInfos.html',
                        controller: 'HrEmpPromotionInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPromotionInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpPromotionInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpPromotionInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpPromotionInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPromotionInfo/hrEmpPromotionInfo-detail.html',
                        controller: 'HrEmpPromotionInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPromotionInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpPromotionInfo', function($stateParams, HrEmpPromotionInfo) {
                        return HrEmpPromotionInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpPromotionInfo.profile', {
                parent: 'hrEmpPromotionInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPromotionInfo/hrEmpPromotionInfo-profile.html',
                        controller: 'HrEmpPromotionInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPromotionInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpPromotionInfo.new', {
                parent: 'hrEmpPromotionInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPromotionInfo/hrEmpPromotionInfo-dialog.html',
                        controller: 'HrEmpPromotionInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPromotionInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            instituteFrom: null,
                            instituteTo: null,
                            departmentFrom: null,
                            departmentTo: null,
                            designationFrom: null,
                            designationTo: null,
                            payscaleFrom: null,
                            payscaleTo: null,
                            joiningDate: null,
                            promotionType: null,
                            officeOrderNo: null,
                            orderDate: null,
                            goDate: null,
                            goDoc: null,
                            goDocContentType: null,
                            goDocName: null,
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
            .state('hrEmpPromotionInfo.edit', {
                parent: 'hrEmpPromotionInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPromotionInfo/hrEmpPromotionInfo-dialog.html',
                        controller: 'HrEmpPromotionInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPromotionInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpPromotionInfo', function($stateParams, HrEmpPromotionInfo) {
                        return HrEmpPromotionInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpPromotionInfo.delete', {
                parent: 'hrEmpPromotionInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empPromotionInfo/hrEmpPromotionInfo-delete-dialog.html',
                        controller: 'HrEmpPromotionInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpPromotionInfo', function(HrEmpPromotionInfo) {
                                return HrEmpPromotionInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpPromotionInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
