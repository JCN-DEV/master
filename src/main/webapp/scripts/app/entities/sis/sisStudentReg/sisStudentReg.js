'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sisStudentReg', {
                parent: 'sis',
                url: '/sisStudentRegs',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisStudentReg.home.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentReg/sisStudentRegs.html',
                        controller: 'SisStudentRegController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentReg');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sisStudentReg.detail', {
                parent: 'sis',
                url: '/sisStudentReg/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisStudentReg.detail.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentReg/sisStudentReg-detail.html',
                        controller: 'SisStudentRegDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentReg');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisStudentReg', function($stateParams, SisStudentReg) {
                        return SisStudentReg.get({id : $stateParams.id});
                    }]
                }
            })
            .state('sisStudentReg.new', {
                parent: 'sisStudentReg',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentReg/sisStudentReg-dialog.html',
                        controller: 'SisStudentRegDialogController'
                    }
                },

                resolve: {
                    entity: function () {
                        return {
                            applicationId: null,
                            instCategory: null,
                            instituteName: null,
                            curriculum: null,
                            TradeTechnology: null,
                            subject1: null,
                            subject2: null,
                            subject3: null,
                            subject4: null,
                            subject5: null,
                            optional: null,
                            shift: null,
                            semester: null,
                            studentName: null,
                            fatherName: null,
                            motherName: null,
                            dateOfBirth: null,
                            gender: null,
                            religion: null,
                            bloodGroup: null,
                            quota: null,
                            nationality: null,
                            mobileNo: null,
                            contactNoHome: null,
                            emailAddress: null,
                            presentAddress: null,
                            permanentAddress: null,
                            activeStatus: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            maritalStatus: null,
                            id: null
                        };
                    }
                }

            })
            .state('sisStudentReg.edit', {
                parent: 'sisStudentReg',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisStudentReg/sisStudentReg-dialog.html',
                        controller: 'SisStudentRegDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentReg');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisStudentReg', function($stateParams, SisStudentReg) {
                        return SisStudentReg.get({id : $stateParams.id});
                    }]
                }

            });
    });
