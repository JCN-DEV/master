'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpPublicationInfo', {
                parent: 'hrm',
                url: '/hrEmpPublicationInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpPublicationInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPublicationInfo/hrEmpPublicationInfos.html',
                        controller: 'HrEmpPublicationInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPublicationInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpPublicationInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpPublicationInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpPublicationInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPublicationInfo/hrEmpPublicationInfo-detail.html',
                        controller: 'HrEmpPublicationInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPublicationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpPublicationInfo', function($stateParams, HrEmpPublicationInfo) {
                        return HrEmpPublicationInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpPublicationInfo.new', {
                parent: 'hrEmpPublicationInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPublicationInfo/hrEmpPublicationInfo-dialog.html',
                        controller: 'HrEmpPublicationInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPublicationInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            publicationTitle: null,
                            publicationDate: null,
                            remarks: null,
                            publicationDoc: null,
                            publicationLink: null,
                            publicationDocContentType: null,
                            publicationDocName: null,
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
            .state('hrEmpPublicationInfo.profile', {
                parent: 'hrEmpPublicationInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPublicationInfo/hrEmpPublicationInfo-profile.html',
                        controller: 'HrEmpPublicationInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPublicationInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpPublicationInfo.edit', {
                parent: 'hrEmpPublicationInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPublicationInfo/hrEmpPublicationInfo-dialog.html',
                        controller: 'HrEmpPublicationInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPublicationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpPublicationInfo', function($stateParams, HrEmpPublicationInfo) {
                        return HrEmpPublicationInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpPublicationInfo.delete', {
                parent: 'hrEmpPublicationInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrEmpPublicationInfo/hrm/empPublicationInfo-delete-dialog.html',
                        controller: 'HrEmpPublicationInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpPublicationInfo', function(HrEmpPublicationInfo) {
                                return HrEmpPublicationInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpPublicationInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
