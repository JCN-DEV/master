'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmployeeInfo', {
                parent: 'hrm',
                url: '/hrEmployeeInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmployeeInfo.home.title',
                    displayName:'Employee Access'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfos.html',
                        controller: 'HrEmployeeInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('jobQuota');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmployeeInfo.detail', {
                parent: 'hrm',
                url: '/hrEmployeeInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmployeeInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-detail.html',
                        controller: 'HrEmployeeInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('jobQuota');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmployeeInfo', function($stateParams, HrEmployeeInfo) {
                        return HrEmployeeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmployeeInfo.register', {
                parent: 'hrm',
                url: '/register',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-register.html',
                        controller: 'HrEmployeeInfoRegisterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('jobQuota');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            fullName: null,
                            fullNameBn: null,
                            fatherName: null,
                            fatherNameBn: null,
                            motherName: null,
                            motherNameBn: null,
                            birthDate: null,
                            apointmentGoDate: null,
                            lastWorkingDay:null,
                            presentId: null,
                            nationalId: null,
                            emailAddress: null,
                            mobileNumber: null,
                            gender: null,
                            birthPlace: null,
                            anyDisease: null,
                            officerStuff: null,
                            tinNumber: null,
                            maritalStatus: null,
                            bloodGroup: null,
                            nationality: null,
                            quota: null,
                            birthCertificateNo: null,
                            religion: null,
                            employeeId: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            organizationType:null,
                            activeAccount:true,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmployeeInfo.new', {
                parent: 'hrEmployeeInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-dialog.html',
                        controller: 'HrEmployeeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('jobQuota');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            fullName: null,
                            fullNameBn: null,
                            fatherName: null,
                            fatherNameBn: null,
                            motherName: null,
                            motherNameBn: null,
                            birthDate: null,
                            apointmentGoDate: null,
                            lastWorkingDay:null,
                            presentId: null,
                            nationalId: null,
                            emailAddress: null,
                            mobileNumber: null,
                            gender: null,
                            birthPlace: null,
                            anyDisease: null,
                            officerStuff: null,
                            tinNumber: null,
                            maritalStatus: null,
                            bloodGroup: null,
                            nationality: null,
                            quota: null,
                            birthCertificateNo: null,
                            religion: null,
                            employeeId: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            organizationType:null,
                            activeAccount:true,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmployeeInfo.general', {
                parent: 'hrEmployeeInfo',
                url: '/general',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    displayName:'Personal Information'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-general.html',
                        controller: 'HrEmployeeInfoProfileController'
                    },
                    'hrmEmpBasicInfoView@hrEmployeeInfo.general': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-profile.html'
                    },
                    'hrmEmpSpouseInfoView@hrEmployeeInfo.general': {
                        templateUrl: 'scripts/app/entities/hrm/spouseInfo/hrSpouseInfo-profile.html',
                        controller: 'HrSpouseInfoProfileController'
                    },
                    'hrmEmpNomineeInfoView@hrEmployeeInfo.general': {
                        templateUrl: 'scripts/app/entities/hrm/nomineeInfo/hrNomineeInfo-profile.html',
                        controller: 'HrNomineeInfoProfileController'
                    },
                    'hrmEmpChildInfoView@hrEmployeeInfo.general': {
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfo-profile.html',
                        controller: 'HrEmpChildrenInfoProfileController'
                    }

                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('jobQuota');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('hrSpouseInfo');
                        $translatePartialLoader.addPart('hrNomineeInfo');
                        $translatePartialLoader.addPart('hrmHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmployeeInfo.profile', {
                parent: 'hrEmployeeInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-profile.html',
                        controller: 'HrEmployeeInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('jobQuota');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            fullName: null,
                            fullNameBn: null,
                            fatherName: null,
                            fatherNameBn: null,
                            motherName: null,
                            motherNameBn: null,
                            birthDate: null,
                            apointmentGoDate: null,
                            lastWorkingDay:null,
                            presentId: null,
                            nationalId: null,
                            emailAddress: null,
                            mobileNumber: null,
                            gender: null,
                            birthPlace: null,
                            anyDisease: null,
                            officerStuff: null,
                            tinNumber: null,
                            maritalStatus: null,
                            bloodGroup: null,
                            nationality: null,
                            quota: null,
                            birthCertificateNo: null,
                            employeeId: null,
                            religion: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            organizationType:null,
                            activeAccount:true,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmployeeInfo.transfer', {
                parent: 'hrEmployeeInfo',
                url: '/transfer',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-transfer.html',
                        controller: 'HrEmployeeInfoTransferController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmployeeInfo.edit', {
                parent: 'hrEmployeeInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-dialog.html',
                        controller: 'HrEmployeeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('jobQuota');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmployeeInfo', function($stateParams, HrEmployeeInfo) {
                        return HrEmployeeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmployeeInfo.delete', {
                parent: 'hrEmployeeInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/employeeInfo/hrEmployeeInfo-delete-dialog.html',
                        controller: 'HrEmployeeInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmployeeInfo', function(HrEmployeeInfo) {
                                return HrEmployeeInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('hrEmployeeInfo', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            });
    });
