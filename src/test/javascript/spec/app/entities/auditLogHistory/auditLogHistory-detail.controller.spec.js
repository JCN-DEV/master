'use strict';

describe('AuditLogHistory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAuditLogHistory, MockAuditLog;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAuditLogHistory = jasmine.createSpy('MockAuditLogHistory');
        MockAuditLog = jasmine.createSpy('MockAuditLog');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AuditLogHistory': MockAuditLogHistory,
            'AuditLog': MockAuditLog
        };
        createController = function() {
            $injector.get('$controller')("AuditLogHistoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:auditLogHistoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
