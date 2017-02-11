'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlEmpReg', {
                parent: 'libraryInfo',
                url: '/dlEmpReg',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlEmpReg.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlEmpReg/dlEmpRegs.html',
                        controller: 'DlEmpRegController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlEmpReg');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dlEmpReg.detail', {
                parent: 'libraryInfo.dlEmpReg',
                url: '/dlEmpReg/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlEmpReg.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlEmpReg/dlEmpReg-detail.html',
                        controller: 'DlEmpRegDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlEmpReg');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlEmpReg', function($stateParams, DlEmpReg) {
                        return DlEmpReg.get({id : $stateParams.id});
                    }]
                }
            })
             .state('libraryInfo.dlEmpReg.new', {
                parent: 'libraryInfo.dlEmpReg',
                url: '/new',
             data: {
                 authorities: [],
                 pageTitle: 'stepApp.dlBookCat.detail.title'
             },
             views: {
                 'libraryView@libraryInfo': {
                     templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlEmpReg/dlEmpReg-dialog.html',
                     controller: 'DlEmpRegDialogController'
                 }
             },
             resolve: {
                 translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                     $translatePartialLoader.addPart('dlEmpReg');
                     return $translate.refresh();
                 }],
                 entity: ['$stateParams', function($stateParams) {

                 }]
             }
         })
               .state('libraryInfo.dlEmpReg.edit', {
                parent: 'libraryInfo.dlEmpReg',
                url: '/{id}/edit',
              data: {
                  authorities: []
              },
              views: {
                  'libraryView@libraryInfo': {
                      templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlEmpReg/dlEmpReg-dialog.html',
                      controller: 'DlEmpRegDialogController'
                  }
              },
              resolve: {
                  translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                      $translatePartialLoader.addPart('dlEmpReg');
                      return $translate.refresh();
                  }],
                  entity: ['$stateParams', 'DlEmpReg', function($stateParams, DlEmpReg) {
                      return DlEmpReg.get({id : $stateParams.id});
                  }]
              }
          });
    });
