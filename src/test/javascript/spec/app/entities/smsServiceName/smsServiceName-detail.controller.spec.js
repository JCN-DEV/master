'use strict';

describe('SmsServiceName Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSmsServiceName, MockSmsServiceType;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSmsServiceName = jasmine.createSpy('MockSmsServiceName');
        MockSmsServiceType = jasmine.createSpy('MockSmsServiceType');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SmsServiceName': MockSmsServiceName,
            'SmsServiceType': MockSmsServiceType
        };
        createController = function() {
            $injector.get('$controller')("SmsServiceNameDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:smsServiceNameUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
