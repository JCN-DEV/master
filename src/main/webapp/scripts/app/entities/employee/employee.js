'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employee', {
                parent: 'entity',
                url: '/employees',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employee/employees.html',
                        controller: 'EmployeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employee.detail', {
                parent: 'entity',
                url: '/employee/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employee.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employee/employee-detail.html',
                        controller: 'EmployeeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        return Employee.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employee.new', {
                parent: 'employee',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employee/employee-dialog.html',
                        controller: 'EmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    nameEnglish: null,
                                    father: null,
                                    mother: null,
                                    presentAddress: null,
                                    permanentAddress: null,
                                    gender: null,
                                    dob: null,
                                    zipCode: null,
                                    registrationCertificateSubject: null,
                                    registrationExamYear: null,
                                    registrationCetificateNo: null,
                                    indexNo: null,
                                    bankName: null,
                                    bankBranch: null,
                                    bankAccountNo: null,
                                    designation: null,
                                    subject: null,
                                    salaryScale: null,
                                    salaryCode: null,
                                    monthlySalaryGovtProvided: null,
                                    monthlySalaryInstituteProvided: null,
                                    gbResolutionReceiveDate: null,
                                    gbResolutionAgendaNo: null,
                                    circularPaperName: null,
                                    circularPublishedDate: null,
                                    recruitExamReceiveDate: null,
                                    dgRepresentativeName: null,
                                    dgRepresentativeDesignation: null,
                                    dgRepresentativeAddress: null,
                                    boardRepresentativeName: null,
                                    boardRepresentativeDesignation: null,
                                    boardRepresentativeAddress: null,
                                    recruitApproveGBResolutionDate: null,
                                    recruitPermitAgendaNo: null,
                                    recruitmentDate: null,
                                    presentInstituteJoinDate: null,
                                    presentPostJoinDate: null,
                                    timeScaleGBResolutionDate: null,
                                    totalJobDuration: null,
                                    currentPositionJobDuration: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('employee', null, { reload: true });
                    }, function() {
                        $state.go('employee');
                    })
                }]
            })
            .state('employee.edit', {
                parent: 'employee',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employee/employee-dialog.html',
                        controller: 'EmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Employee', function(Employee) {
                                return Employee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('employee.delete', {
                parent: 'employee',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employee/employee-delete-dialog.html',
                        controller: 'EmployeeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Employee', function(Employee) {
                                return Employee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
