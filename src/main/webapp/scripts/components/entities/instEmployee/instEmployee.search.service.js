'use strict';

angular.module('stepApp')
    .factory('InstEmployeeSearch', function ($resource) {
        return $resource('api/_search/instEmployees/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstEmployeeCode', function ($resource) {
        return $resource('api/instEmployees/code/:code', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstEmployeeOfCurrentInstByCode', function ($resource) {
        return $resource('api/instEmployees/institute/employee/:code', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstEmployeeOfCurrentInstByIndex', function ($resource) {
        return $resource('api/instEmployees/institute/current/employee/:index', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstEmployeeByIndex', function ($resource) {
        return $resource('api/instEmployees/indexNo/:indexNo', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstEmployeeOneByIndex', function ($resource) {
        return $resource('api/instEmployees/employee/:indexNo', {}, {
            'get': { method: 'GET', isArray: false}
        });
    })
    .factory('CurrentInstEmployee', function ($resource) {
        return $resource('api/instEmployees/current', {}, {
            'get': { method: 'GET', isArray: false}
        });
    })
    .factory('InstEmployeePendingList', function ($resource) {
        return $resource('api/instEmployees/pendingList/:id', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('InstEmployeeApproveList', function ($resource) {
        return $resource('api/instEmployees/approveList/:id', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('InstStaffApproveList', function ($resource) {
        return $resource('api/instEmployees/staffApproveList/:id', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('InstEmployeeDeclineList', function ($resource) {
        return $resource('api/instEmployees/declinedList/:id', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('getEmplInfoByCode', function ($resource) {
      return $resource('api/instEmployees/getEmplInfoByCode/:code', {}, {
          'get': { method: 'GET', isArray: false}
      });
  }).factory('AllMpoEnlistedEmployeeOfCurrent', function ($resource) {
      return $resource('api/instEmployees/current/mpoEnlisted', {}, {
          'query': { method: 'GET', isArray: true}
      });
  }).factory('JpAdminsByInstituteId', function ($resource) {
      return $resource('api/instEmployees/jpadmin/institute/:id', {}, {
          'query': { method: 'GET', isArray: true}
      });
  });
