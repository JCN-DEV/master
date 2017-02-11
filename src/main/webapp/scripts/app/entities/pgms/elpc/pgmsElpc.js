'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsElpc', {
                parent: 'pgms',
                url: '/pgmsElpcs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsElpc.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/elpc/pgmsElpcs.html',
                        controller: 'PgmsElpcController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsElpc');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsElpc.detail', {
                parent: 'pgms',
                url: '/pgmsElpc/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsElpc.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/elpc/pgmsElpc-detail.html',
                        controller: 'PgmsElpcDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsElpc');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsElpc', function($stateParams, PgmsElpc) {
                        return PgmsElpc.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsElpc.new', {
                parent: 'pgmsElpc',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/elpc/pgmsElpc-dialog.html',
                      controller: 'PgmsElpcDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsElpc');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    empCode: null,
                                    instCode: null,
                                    empName: null,
                                    instName: null,
                                    desigId: null,
                                    designation: null,
                                    dateOfBirth: null,
                                    joinDate: null,
                                    beginDateOfRetiremnt: null,
                                    retirementDate: null,
                                    lastRcvPayscale: null,
                                    incrsDtOfYrlyPayment: null,
                                    gainingLeave: null,
                                    leaveType: null,
                                    leaveTotal: null,
                                    appRetirementDate: null,
                                    mainPayment: null,
                                    incrMonRateLeaving: null,
                                    specialPayment: null,
                                    specialAllowance: null,
                                    houserentAl: null,
                                    treatmentAl: null,
                                    dearnessAl: null,
                                    travellingAl: null,
                                    laundryAl: null,
                                    personalAl: null,
                                    technicalAl: null,
                                    hospitalityAl: null,
                                    tiffinAl: null,
                                    advOfMakingHouse: null,
                                    vechileStatus: null,
                                    advTravAl: null,
                                    advSalary: null,
                                    houseRent: null,
                                    carRent: null,
                                    gasBill: null,
                                    santryWaterTax: null,
                                    bankAcc: null,
                                    accBookNo: null,
                                    bookPageNo: null,
                                    bankInterest: null,
                                    monlyDepRateFrSalary: null,
                                    expectedDeposition: null,
                                    accDate: null,
                                    appNo: null,
                                    appDate: null,
                                    appType: null,
                                    appComments: null,
                                    aprvStatus: 'Pending',
                                    aprvDate: null,
                                    aprvComment: null,
                                    aprvBy: null,
                                    notificationStatus: null,
                                    activeStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateBy: null,
                                    updateDate: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsElpc.edit', {
                parent: 'pgmsElpc',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/elpc/pgmsElpc-dialog.html',
                          controller: 'PgmsElpcDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsElpc');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsElpc', function($stateParams,PgmsElpc) {
                               return PgmsElpc.get({id : $stateParams.id});
                     }]
                }
            });
    });
