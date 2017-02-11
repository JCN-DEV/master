'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrSpouseInfo', {
                parent: 'hrm',
                url: '/hrSpouseInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrSpouseInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/spouseInfo/hrSpouseInfos.html',
                        controller: 'HrSpouseInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrSpouseInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrSpouseInfo.detail', {
                parent: 'hrm',
                url: '/hrSpouseInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrSpouseInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/spouseInfo/hrSpouseInfo-detail.html',
                        controller: 'HrSpouseInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrSpouseInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrSpouseInfo', function($stateParams, HrSpouseInfo) {
                        return HrSpouseInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrSpouseInfo.new', {
                parent: 'hrSpouseInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/spouseInfo/hrSpouseInfo-dialog.html',
                        controller: 'HrSpouseInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrSpouseInfo');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            spouseName: null,
                            spouseNameBn: null,
                            birthDate: null,
                            gender: null,
                            relationship: null,
                            isNominee: null,
                            occupation: null,
                            organization: null,
                            designation: null,
                            nationalId: null,
                            emailAddress: null,
                            contactNumber: null,
                            address: null,
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
            .state('hrSpouseInfo.profile', {
                parent: 'hrSpouseInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/spouseInfo/hrSpouseInfo-profile.html',
                        controller: 'HrSpouseInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrSpouseInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrSpouseInfo.edit', {
                parent: 'hrSpouseInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/spouseInfo/hrSpouseInfo-dialog.html',
                        controller: 'HrSpouseInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrSpouseInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrSpouseInfo', function($stateParams, HrSpouseInfo) {
                        return HrSpouseInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrSpouseInfo.delete', {
                parent: 'hrSpouseInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/spouseInfo/hrSpouseInfo-delete-dialog.html',
                        controller: 'HrSpouseInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrSpouseInfo', function(HrSpouseInfo) {
                                return HrSpouseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrSpouseInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
