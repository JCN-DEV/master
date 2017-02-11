'use strict';

angular.module('stepApp')
    .factory('HrEmployeeInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmployeeInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthDate = DateUtils.convertLocaleDateFromServer(data.birthDate);
                    data.apointmentGoDate = DateUtils.convertLocaleDateFromServer(data.apointmentGoDate);
                    data.dateOfJoining = DateUtils.convertLocaleDateFromServer(data.dateOfJoining);
                    data.prlDate    = DateUtils.convertLocaleDateFromServer(data.prlDate);
                    data.actionDate = DateUtils.convertLocaleDateFromServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.apointmentGoDate = DateUtils.convertLocaleDateToServer(data.apointmentGoDate);
                    data.dateOfJoining = DateUtils.convertLocaleDateToServer(data.dateOfJoining);
                    data.prlDate    = DateUtils.convertLocaleDateToServer(data.prlDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.apointmentGoDate = DateUtils.convertLocaleDateToServer(data.apointmentGoDate);
                    data.dateOfJoining = DateUtils.convertLocaleDateToServer(data.dateOfJoining);
                    data.prlDate    = DateUtils.convertLocaleDateToServer(data.prlDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('HrEmployeeInfoByDateRange', function ($resource, DateUtils)
    {
        return $resource('api/hrEmployeeInfosByDateRange/', {},
        {
            'update': {
                method: 'POST',
                isArray: true,
                transformRequest: function (data)
                {
                    data.minDate = DateUtils.convertLocaleDateToServer(data.minDate);
                    data.maxDate = DateUtils.convertLocaleDateToServer(data.maxDate);
                    return angular.toJson(data);
                },
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthDate = DateUtils.convertLocaleDateFromServer(data.birthDate);
                    data.apointmentGoDate = DateUtils.convertLocaleDateFromServer(data.apointmentGoDate);
                    data.dateOfJoining = DateUtils.convertLocaleDateFromServer(data.dateOfJoining);
                    data.prlDate    = DateUtils.convertLocaleDateFromServer(data.prlDate);
                    data.actionDate = DateUtils.convertLocaleDateFromServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            }
        });
    })
    .factory('HrEmployeeInfoApprover', function ($resource)
    {
        return $resource('api/hrEmployeeInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    })
    .factory('HrEmployeeInfoByEmployeeId', function ($resource)
    {
        return $resource('api/hrEmployeeInfos/findEmployees/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true},
                'get': { method: 'GET'}
            });
    })
    .factory('HrEmployeeInfoOnOff', function ($resource)
    {
        return $resource('api/hrEmployeeInfosOffOn/', {},
            {
                'update': { method: 'POST'}
            });
    })
    .factory('HrEmployeeInfoApproverLog', function ($resource)
    {
        return $resource('api/hrEmployeeInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    })
    .factory('HrEmployeeInfoAppRejDashboard', function ($resource)
    {
        return $resource('api/hrEmployeeInfosDashboard/', {},
        {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('HrEmployeeInfoDetail', function ($resource)
    {
        return $resource('api/hrEmployeeInfosDetail/', {},
            {
                'query': { method: 'GET', isArray: true}
            });
    })
    .factory('HrEmployeeInfoByFilter', function ($resource)
    {
        return $resource('api/hrEmployeeInfosByFilter/:fieldName/:fieldValue', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    .factory('HrEmployeeInfoByWorkArea', function ($resource)
    {
        return $resource('api/hrEmployeeInfosListByWorkArea/:areaid', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    .factory('HrEmployeeInfoDesigLimitCheck', function ($resource){
        return $resource('api/hrEmployeeInfosDesigCheck/:orgtype/:desigId/:refid', {},
        {
            'get': { method: 'GET'}
        });
    })
    .factory('CurrentHrEmployeeInfo', function ($resource) {  // Added By Bappi Mazumder for Employee Loan
        return $resource('api/hrEmployeeInfos/my', {}, {
            'get': { method: 'GET'}
        });
    })
    .factory('HrEmployeeInfosByDesigLevel', function ($resource)
    {
        return $resource('api/hrEmployeeInfosListByDesigLevel/:desigLevel', {},
        {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('hrEmployeeInfosByDesigType', function ($resource)
    {
        return $resource('api/hrEmployeeInfosListByDesigType/:desigType', {},
        {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('HrEmployeeInfoTransfer', function ($resource)
    {
        return $resource('api/hrEmployeeInfoTransfer/', {},
            {
                'update': { method: 'POST'}
            });
    });
