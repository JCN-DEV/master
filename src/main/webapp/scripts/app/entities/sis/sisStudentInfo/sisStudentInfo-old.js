'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sisStudentInfo', {
                parent: 'sis',
                url: '/sisStudentInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.sisStudentInfo.home.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentInfo/sisStudentInfos.html',
                        controller: 'SisStudentInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('religions');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sisStudentInfo.detail', {
                parent: 'sis',
                url: '/sisStudentInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.sisStudentInfo.detail.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentInfo/sisStudentInfo-detail.html',
                        controller: 'SisStudentInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('religions');
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
                    authorities: ['ROLE_USER']
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentInfo/sisStudentInfo-dialog.html',
                        controller: 'SisStudentInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('religions');
                        return $translate.refresh();
                    }],
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
                            activeStatus: true,
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
            })
            .state('sisStudentInfo.edit', {
                parent: 'sisStudentInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentInfo/sisStudentInfo-dialog.html',
                        controller: 'SisStudentInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('religions');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisStudentInfo', function($stateParams, SisStudentInfo) {
                        return SisStudentInfo.get({id : $stateParams.id});
                    }]
                }
            });
    });