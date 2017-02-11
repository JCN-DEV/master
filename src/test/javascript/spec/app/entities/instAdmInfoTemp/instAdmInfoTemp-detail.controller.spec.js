'use strict';

describe('InstAdmInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstAdmInfoTemp, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstAdmInfoTemp = jasmine.createSpy('MockInstAdmInfoTemp');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstAdmInfoTemp': MockInstAdmInfoTemp,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InstAdmInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instAdmInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
