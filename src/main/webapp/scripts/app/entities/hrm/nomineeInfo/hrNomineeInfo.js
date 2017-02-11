'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrNomineeInfo', {
                parent: 'hrm',
                url: '/hrNomineeInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrNomineeInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/nomineeInfo/hrNomineeInfos.html',
                        controller: 'HrNomineeInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrNomineeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrNomineeInfo.detail', {
                parent: 'hrm',
                url: '/hrNomineeInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrNomineeInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/nomineeInfo/hrNomineeInfo-detail.html',
                        controller: 'HrNomineeInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrNomineeInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrNomineeInfo', function($stateParams, HrNomineeInfo) {
                        return HrNomineeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrNomineeInfo.profile', {
                parent: 'hrNomineeInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/nomineeInfo/hrNomineeInfo-profile.html',
                        controller: 'HrNomineeInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrNomineeInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrNomineeInfo.new', {
                parent: 'hrNomineeInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/nomineeInfo/hrNomineeInfo-dialog.html',
                        controller: 'HrNomineeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrNomineeInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            nomineeName: null,
                            nomineeNameBn: null,
                            birthDate: null,
                            gender: null,
                            relationship: null,
                            occupation: null,
                            designation: null,
                            nationalId: null,
                            mobileNumber: null,
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
            .state('hrNomineeInfo.edit', {
                parent: 'hrNomineeInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/nomineeInfo/hrNomineeInfo-dialog.html',
                        controller: 'HrNomineeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrNomineeInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrNomineeInfo', function($stateParams, HrNomineeInfo) {
                        return HrNomineeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrNomineeInfo.delete', {
                parent: 'hrNomineeInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/nomineeInfo/hrNomineeInfo-delete-dialog.html',
                        controller: 'HrNomineeInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrNomineeInfo', function(HrNomineeInfo) {
                                return HrNomineeInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrNomineeInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
