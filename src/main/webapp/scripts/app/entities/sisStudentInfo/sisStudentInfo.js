'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sisStudentInfo', {
                parent: 'entity',
                url: '/sisStudentInfos',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisStudentInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sisStudentInfo/sisStudentInfos.html',
                        controller: 'SisStudentInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sisStudentInfo.detail', {
                parent: 'entity',
                url: '/sisStudentInfo/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisStudentInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sisStudentInfo/sisStudentInfo-detail.html',
                        controller: 'SisStudentInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisStudentInfo', function($stateParams, SisStudentInfo) {
                        return SisStudentInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('sisStudentInfo.new', {
                parent: 'sisStudentInfo',
                url: '/new',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sisStudentInfo/sisStudentInfo-dialog.html',
                        controller: 'SisStudentInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    stuPicture: null,
                                    stuPictureContentType: null,
                                    instituteName: null,
                                    TradeTechnology: null,
                                    studentName: null,
                                    fatherName: null,
                                    motherName: null,
                                    dateOfBirth: null,
                                    presentAddress: null,
                                    permanentAddress: null,
                                    nationality: null,
                                    nationalIdNo: null,
                                    birthCertificateNo: null,
                                    mobileNo: null,
                                    contactNoHome: null,
                                    emailAddress: null,
                                    gender: null,
                                    maritalStatus: null,
                                    bloodGroup: null,
                                    religion: null,
                                    activeStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    curriculum: null,
                                    applicationId: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('sisStudentInfo', null, { reload: true });
                    }, function() {
                        $state.go('sisStudentInfo');
                    })
                }]
            })
            .state('sisStudentInfo.edit', {
                parent: 'sisStudentInfo',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sisStudentInfo/sisStudentInfo-dialog.html',
                        controller: 'SisStudentInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SisStudentInfo', function(SisStudentInfo) {
                                return SisStudentInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('sisStudentInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
