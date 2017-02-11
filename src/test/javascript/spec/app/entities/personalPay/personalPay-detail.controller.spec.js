'use strict';

describe('PersonalPay Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPersonalPay, MockPayScale, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPersonalPay = jasmine.createSpy('MockPersonalPay');
        MockPayScale = jasmine.createSpy('MockPayScale');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PersonalPay': MockPersonalPay,
            'PayScale': MockPayScale,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("PersonalPayDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:personalPayUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
