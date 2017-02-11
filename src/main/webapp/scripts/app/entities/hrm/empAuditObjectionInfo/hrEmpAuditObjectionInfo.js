'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpAuditObjectionInfo', {
                parent: 'hrm',
                url: '/hrEmpAuditObjectionInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAuditObjectionInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAuditObjectionInfo/hrEmpAuditObjectionInfos.html',
                        controller: 'HrEmpAuditObjectionInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAuditObjectionInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAuditObjectionInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpAuditObjectionInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAuditObjectionInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAuditObjectionInfo/hrEmpAuditObjectionInfo-detail.html',
                        controller: 'HrEmpAuditObjectionInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAuditObjectionInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAuditObjectionInfo', function($stateParams, HrEmpAuditObjectionInfo) {
                        return HrEmpAuditObjectionInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAuditObjectionInfo.new', {
                parent: 'hrEmpAuditObjectionInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAuditObjectionInfo/hrEmpAuditObjectionInfo-dialog.html',
                        controller: 'HrEmpAuditObjectionInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAuditObjectionInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            organizationName: null,
                            auditYear: null,
                            paragraphNumber: null,
                            objectionHeadliine: null,
                            objectionAmount: null,
                            officeReplyNumber: null,
                            replyDate: null,
                            jointMeetingNumber: null,
                            jointMeetingDate: null,
                            isSettled: null,
                            remarks: null,
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
            .state('hrEmpAuditObjectionInfo.edit', {
                parent: 'hrEmpAuditObjectionInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAuditObjectionInfo/hrEmpAuditObjectionInfo-dialog.html',
                        controller: 'HrEmpAuditObjectionInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAuditObjectionInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAuditObjectionInfo', function($stateParams, HrEmpAuditObjectionInfo) {
                        return HrEmpAuditObjectionInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAuditObjectionInfo.delete', {
                parent: 'hrEmpAuditObjectionInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empAuditObjectionInfo/hrEmpAuditObjectionInfo-delete-dialog.html',
                        controller: 'HrEmpAuditObjectionInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpAuditObjectionInfo', function(HrEmpAuditObjectionInfo) {
                                return HrEmpAuditObjectionInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpAuditObjectionInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
